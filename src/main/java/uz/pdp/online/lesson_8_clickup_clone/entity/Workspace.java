package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsLongEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//qaysi columnlar birga kelsa takrorlanmasligi
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name","owner_id"})})// 1 necha ustunlarni unique qilmoqchimiz
public class Workspace extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String  color;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User owner;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment avatar;// avatar

    @PrePersist// dbga qo'shishdan oldin
    @PreUpdate// dbda update qilishdan oldin
    public void setInitialLetterMyMethod() {
        this.initialLetter = name.substring(0,1);
    }

    public Workspace(String name, String color, User owner, Attachment avatar) {
        this.name = name;
        this.color = color;
        this.owner = owner;
        this.avatar = avatar;
    }
}
