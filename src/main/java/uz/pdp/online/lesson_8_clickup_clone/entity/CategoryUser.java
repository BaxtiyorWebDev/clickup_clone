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
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryUser extends AbsLongEntity {

//    @Column(nullable = false)
//   private String name;
//
//    @ManyToOne
//    private Category category;
//
//    @ManyToOne
//    private User user;
//
//    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    private List<WorkspacePermissionName> permission;


}
