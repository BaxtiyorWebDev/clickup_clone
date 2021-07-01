package uz.pdp.online.lesson_8_clickup_clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_8_clickup_clone.entity.*;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.AddType;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspacePermissionName;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspaceRoleName;
import uz.pdp.online.lesson_8_clickup_clone.payload.*;
import uz.pdp.online.lesson_8_clickup_clone.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    WorkspaceRepos workspaceRepos;
    @Autowired
    AttachmentRepos attachmentRepos;
    @Autowired
    WorkspaceUserRepos workspaceUserRepos;
    @Autowired
    WorkspaceRoleRepos workspaceRoleRepos;
    @Autowired
    WorkspacePermissionRepos workspacePermissionRepos;
    @Autowired
    UserRepos userRepos;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user) {
        //WORKSPACE OCHDIK
        if (workspaceRepos.existsByOwnerIdAndName(user.getId(), workspaceDto.getName()))
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        Workspace workspace = new Workspace(
                workspaceDto.getName(),
                workspaceDto.getColor(),
                user,
                workspaceDto.getAvatarId()==null?null:attachmentRepos.findById(workspaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepos.save(workspace);

        // WORKSPACE ROLE OCDHIK

        WorkspaceRole ownerRole = workspaceRoleRepos.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));

        WorkspaceRole adminRole = workspaceRoleRepos.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepos.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepos.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        // OWNERGA HUQUQLARNI BERYAPMIZ
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName);
            workspacePermissions.add(workspacePermission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)){
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)){
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)){
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName));
            }
        }

        workspacePermissionRepos.saveAll(workspacePermissions);



        //WORKSPACE USER OCHDIK
        workspaceUserRepos.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));
        return new ApiResponse("Ishxona saqlandi",true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto, User user) {

        Optional<Workspace> optionalWorkspace = workspaceRepos.findById(id);
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("Bunday ishxona topilmadi",false);
        Workspace editingWorkspace = optionalWorkspace.get();
        boolean equals = editingWorkspace.getOwner().getId().equals(user.getId());
        if (!equals)
            return new ApiResponse("Siz ushbu ishxonani o'zgaritira olmaysiz",false);

        boolean existsByOwnerIdAndNameAndIdNot = workspaceRepos.existsByOwnerIdAndNameAndIdNot(user.getId(), workspaceDto.getName(), id);
        if (existsByOwnerIdAndNameAndIdNot)
            return new ApiResponse("Bunday ishxona sizda mavjud",false);
        editingWorkspace.setName(workspaceDto.getName());
        editingWorkspace.setColor(editingWorkspace.getColor());
        editingWorkspace.setAvatar(workspaceDto.getAvatarId()==null?null:attachmentRepos.findById(workspaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        workspaceRepos.save(editingWorkspace);
        return new ApiResponse("Ishxona tahrirlandi",true);
    }


    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId, User currentUser) {
        Optional<Workspace> optionalWorkspace = workspaceRepos.findById(id);
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("Bunday ishxona mavjud emas",false);
        Optional<User> optionalUser = userRepos.findById(ownerId);
        if (!optionalUser.isPresent())
            return new ApiResponse("Bunday user mavjud emas",false);
        Workspace workspace = optionalWorkspace.get();
        if (!currentUser.getId().equals(workspace.getOwner().getId()))
            return new ApiResponse("Siz ushbu amalni bajara olmaysiz",false);
        User user = optionalUser.get();
        workspace.setOwner(user);
        workspaceRepos.save(workspace);
        return new ApiResponse("Ishxona egasi o'zgartirildi",true);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id, User user) {
        try {
            Optional<Workspace> optionalWorkspace = workspaceRepos.findById(id);
            Workspace workspace = optionalWorkspace.get();
            boolean equals = workspace.getOwner().getId().equals(user.getId());
            if (equals) {
                workspaceRepos.deleteById(id);
                return new ApiResponse("O'chirildi", true);
            } else {
                return new ApiResponse("Ishxonani o'chira olmaysiz",false);
            }
        }catch (Exception e) {
            return new ApiResponse("Xatolik",false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDto memberDto, User currentUser) {
        Optional<Workspace> optionalWorkspace = workspaceRepos.findById(id);
        Workspace workspace = optionalWorkspace.get();
        if (!workspace.getOwner().getId().equals(currentUser.getId()))
            return new ApiResponse("Sizda ushbu so'rovni amalga oshirish imkoni yo'q",false);
        if (memberDto.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepos.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepos.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
            workspaceUserRepos.save(workspaceUser);
//            todo EMAILGA INVITE XABAR YUBORISH
        } else if (memberDto.getAddType().equals(AddType.EDIT)) {

            WorkspaceUser workspaceUser = workspaceUserRepos.findByWorkspaceIdAndUserId(id,memberDto.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepos.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepos.save(workspaceUser);
        } else if (memberDto.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepos.deleteByWorkspaceIdAndUserId(id,memberDto.getId());
        }
        return new ApiResponse("Muvaffaqqiyatli",true);
    }


    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepos.findByWorkspaceIdAndUserId(id, user.getId());
        if(optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDate_joined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepos.save(workspaceUser);
            return new ApiResponse("Success",true);
        }
        return new ApiResponse("Error",false);
    }

    @Override
    public List<User> viewMembersAndGuests(Long id, User currentUser) {
        Optional<Workspace> optionalWorkspace = workspaceRepos.findById(id);
        Workspace workspace = optionalWorkspace.get();
        if (!workspace.getOwner().getId().equals(currentUser.getId()))
            return null;
        List<WorkspaceUser> allByWorkspaceId = workspaceUserRepos.findAllByWorkspaceId(id);
        List<User> userList = new ArrayList<>();
        for (WorkspaceUser workspaceUser : allByWorkspaceId) {
            User user = workspaceUser.getUser();
            userList.add(user);
        }
        return userList;
    }


    @Override
    public List<Workspace> getWorkspaceList(User user) {
        List<Workspace> allByUserId = workspaceUserRepos.findAllByUserId(user.getId());
        return allByUserId;
    }

    @Override
    public ApiResponse addRoleToWorkspace(Long id, RoleDto roleDto, User user) {
        Optional<Workspace> optionalWorkspace = workspaceRepos.findById(id);
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("Bunday ishxona topilmadi",false);
        Workspace workspace = optionalWorkspace.get();
        if (!workspace.getOwner().getId().equals(user.getId()))
            return new ApiResponse("Sizda ushbu so'rovni amalga oshirish imkoni mavjud emas",false);
        WorkspaceRole workspaceRole = new WorkspaceRole();
        workspaceRole.setWorkspace(workspace);
        workspaceRole.setName(roleDto.getName());

        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();
        if (roleDto.getId()==1) {
            workspaceRole.setExtendsRole(WorkspaceRoleName.ROLE_OWNER);
            for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
                WorkspacePermission workspacePermission = new WorkspacePermission(
                        workspaceRole,
                        workspacePermissionName);
                workspacePermissions.add(workspacePermission);
            }
        } else if (roleDto.getId()==2) {
            workspaceRole.setExtendsRole(WorkspaceRoleName.ROLE_ADMIN);
            for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
                if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                    workspacePermissions.add(new WorkspacePermission(
                            workspaceRole,
                            workspacePermissionName));
                }
            }
        } else if (roleDto.getId()==3) {
            workspaceRole.setExtendsRole(WorkspaceRoleName.ROLE_MEMBER);
            for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
                if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                    workspacePermissions.add(new WorkspacePermission(
                            workspaceRole,
                            workspacePermissionName));
                }
            }
        } else if (roleDto.getId()==4) {
            workspaceRole.setExtendsRole(WorkspaceRoleName.ROLE_GUEST);
            for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
                if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                    workspacePermissions.add(new WorkspacePermission(
                            workspaceRole,
                            workspacePermissionName));
                }
            }
        }
        workspaceRoleRepos.save(workspaceRole);
        workspacePermissionRepos.saveAll(workspacePermissions);
        return new ApiResponse("Yangi role saqlandi",true);
    }

    @Override
    public ApiResponse addOrRemovePermission(WorkspaceRoleDto workspaceRoleDto, User user) {
        WorkspaceRole workspaceRole = workspaceRoleRepos.findById(workspaceRoleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("workspaceRole"));
        Workspace workspace = workspaceRole.getWorkspace();
        if (!workspace.getOwner().getId().equals(user.getId()))
            return new ApiResponse("Sizda ushbu so'rovni amalga oshirish imkoni mavjud emas",false);
        Optional<WorkspacePermission> byWorkspaceRoleIdAndPermission = workspacePermissionRepos.findByWorkspaceRoleIdAndPermission(workspaceRole.getId(), workspaceRoleDto.getWorkspacePermissionName());
        if (workspaceRoleDto.getAddType().equals(AddType.ADD)) {
            if (byWorkspaceRoleIdAndPermission.isPresent())
                return new ApiResponse("Bunday priviligiya avval qo'shilgan",false);
            WorkspacePermission workspacePermission = new WorkspacePermission(workspaceRole, workspaceRoleDto.getWorkspacePermissionName());
            workspacePermissionRepos.save(workspacePermission);
            return new ApiResponse("Priviligiya qo'shildi",true);
        } else if (workspaceRoleDto.getAddType().equals(AddType.REMOVE)) {
            if (byWorkspaceRoleIdAndPermission.isPresent()) {
                workspacePermissionRepos.delete(byWorkspaceRoleIdAndPermission.get());
                return new ApiResponse("Muvaffaqqiyatli o'chirildi",true);
            }
            return new ApiResponse("Bunday obekt topilmadi",false);
        }
        return null;
    }
}
