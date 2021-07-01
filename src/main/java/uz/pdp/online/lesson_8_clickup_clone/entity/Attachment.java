package uz.pdp.online.lesson_8_clickup_clone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.lesson_8_clickup_clone.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Attachment extends AbsUUIDEntity {

    //clientga ko'rsatish uchun
    private String originalName; // pdp.jpg

    private Long size;// 2048000

    private String contentType; // aplication/pdf || image/jpg

    //papkani ichidan topish uchun
    private String name; // bitta nomlik 2 ta fayl bo'lib qolishini oldini olish uchun

}
