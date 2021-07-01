package uz.pdp.online.lesson_8_clickup_clone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_8_clickup_clone.entity.Workspace;
import uz.pdp.online.lesson_8_clickup_clone.payload.*;
import uz.pdp.online.lesson_8_clickup_clone.entity.User;
import uz.pdp.online.lesson_8_clickup_clone.security.CurrentUser;
import uz.pdp.online.lesson_8_clickup_clone.service.WorkspaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {

    @Autowired
    WorkspaceService workspaceService;

    @PostMapping
    public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(workspaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * NAME, COLOR, AVATAR O'ZGARISHI MUMKIN
     *
     * @param id
     * @param workspaceDto
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestBody WorkspaceDto workspaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.editWorkspace(id, workspaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @param ownerId
     * @return
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id,
                                              @RequestParam UUID ownerId) {
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    /**
     * ISHXONANI O'CHIRISH
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id,
                                                    @RequestBody MemberDto memberDto) {
        ApiResponse apiResponse = workspaceService.addOrEditOrRemoveWorkspace(id, memberDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * WORKSPACE ID BILAN MEMBER VA GUESTLARNI KO'RISH
     * @param id
     * @return
     */
    @GetMapping("/viewMembersAndGuests")
    public HttpEntity<?> viewMembersAndGuests(@PathVariable Long id) {
        List<User> membersAndGuests = workspaceService.viewMembersAndGuests(id);
        return ResponseEntity.status(membersAndGuests!=null?200:409).body(membersAndGuests);
    }

    /**
     * WORKSPACELAR RO'YXATINI OLISH BY USER
     * @param user
     * @return
     */
    @GetMapping("/getList")
    public HttpEntity<?> getWorkspaceList(@CurrentUser User user) {
       List<Workspace> workspaceList= workspaceService.getWorkspaceList(user);
       return ResponseEntity.status(workspaceList!=null?200:409).body(workspaceList);
    }

    /**
     *
     * @param id WORKSPACE ID
     * @param roleDto ROLE NAME AND EXTENDS ROLE ID
     * @return
     */
    @PostMapping("/addRole/{id}")
    public HttpEntity<?> addRoleToWorkspace(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = workspaceService.addRoleToWorkspace(id,roleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    /**
     * ROLE BERISH / OLIB TASHLASH
     * @param workspaceRoleDto
     * @return
     */
    @PutMapping("/addOrRemovePermission")
    public HttpEntity<?> addOrRemovePermission(@RequestBody WorkspaceRoleDto workspaceRoleDto) {
        ApiResponse apiResponse = workspaceService.addOrRemovePermission(workspaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
