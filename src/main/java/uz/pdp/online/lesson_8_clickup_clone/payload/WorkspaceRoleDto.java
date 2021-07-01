package uz.pdp.online.lesson_8_clickup_clone.payload;

import lombok.Data;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.AddType;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspacePermissionName;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspaceRoleName;

import java.util.UUID;

@Data
public class WorkspaceRoleDto {
    private UUID id;
    private String name;
    private WorkspaceRoleName extendsRole;
    private WorkspacePermissionName workspacePermissionName;
    private AddType addType;
}
