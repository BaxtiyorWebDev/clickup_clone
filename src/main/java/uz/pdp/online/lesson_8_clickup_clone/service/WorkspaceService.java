package uz.pdp.online.lesson_8_clickup_clone.service;

import uz.pdp.online.lesson_8_clickup_clone.entity.Workspace;
import uz.pdp.online.lesson_8_clickup_clone.payload.*;
import uz.pdp.online.lesson_8_clickup_clone.entity.User;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user);


    ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto, User user);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDto memberDto);

    ApiResponse joinToWorkspace(Long id, User user);

    List<User> viewMembersAndGuests(Long id);

    List<Workspace> getWorkspaceList(User user);

    ApiResponse addRoleToWorkspace(Long id, RoleDto roleDto);

    ApiResponse addOrRemovePermission(WorkspaceRoleDto workspaceRoleDto);
}
