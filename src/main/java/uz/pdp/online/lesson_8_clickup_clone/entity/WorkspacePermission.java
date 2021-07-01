package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspacePermissionName;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class WorkspacePermission extends AbsLongEntity {/*ROLE VA PERMISSIONNI BIRLASHTIRISH UCHUN*/


    @ManyToOne
    private WorkspaceRole workspaceRole;// O'RINBOSAR,

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;// ADD_MEMBER, REMOVE_MEMBER

}
