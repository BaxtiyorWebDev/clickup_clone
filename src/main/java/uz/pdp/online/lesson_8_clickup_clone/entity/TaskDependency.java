package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.DependencyType;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskDependency extends AbsLongEntity {

//    @ManyToOne
//    private Task task;
//
//    @ManyToOne
//    private Task dependencyTask;
//
//    private DependencyType dependencyType;

}