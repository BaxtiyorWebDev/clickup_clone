package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Icon extends AbsLongEntity {
//
//    @ManyToOne
//    private Attachment attachment;
//
//    private String color;
}
