package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task extends AbsLongEntity {

//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String description;
//
//    @ManyToOne
//    private Status status;
//
//    @ManyToOne
//    private Category category;
//
//    @ManyToOne
//    private Priority priority;
//
//    private String color;
//
//    private Long parentTaskId;
//
//    private Timestamp startedDate;
//
//    private boolean startTimeHas;
//
//    private Timestamp dueDate;
//
//    private boolean dueTimeHas;
//
//    private Long estimateTime;
//
//    private Timestamp activatedDate;

}
