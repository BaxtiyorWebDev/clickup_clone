package uz.pdp.online.lesson_8_clickup_clone.payload;

import lombok.Data;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.AddType;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspaceRoleName;

import java.util.UUID;

@Data
public class MemberDto {
    private UUID id;
    private UUID roleId;
    private AddType addType;//REMOVE, ADD, EDIT
}
