package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsLongEntity {

//    @Column(nullable = false)
//    private String name;
//
//    @ManyToOne
//    private Project project;
//
//    private String accessType;
//
//    private boolean archived;
//
//    private String color;

}
