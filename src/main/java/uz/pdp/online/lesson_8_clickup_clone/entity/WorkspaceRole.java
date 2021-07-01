package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspaceRoleName;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceRole extends AbsUUIDEntity {

    /*KO'PLAB LAVOZIMLAR 1 TA ISHXONAGA*/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workspace workspace;

    @Column(nullable = false)
    private String name; // yangi ro'l ochganda ishlatiladi

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsRole;// dastlab extendsRole == null

}
