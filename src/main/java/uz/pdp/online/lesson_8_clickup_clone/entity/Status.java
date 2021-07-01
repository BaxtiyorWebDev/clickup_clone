package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.StatusType;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Status extends AbsLongEntity {

//    private String name;
//
//    @ManyToOne
//    private Space space;
//
//    @ManyToOne
//    private Project project;
//
//    @ManyToOne
//    private Category category;
//
//    private String color;
//
//
//    private StatusType statusType;
    
}
