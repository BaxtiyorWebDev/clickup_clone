package uz.pdp.online.lesson_8_clickup_clone.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass// ota class sifatida bilishi uchun
public abstract class AbsUUIDEntity extends AbsMainEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "org.hibernate.type.PostgresUUIDType")// qaysi typeda generation qilishi
    @GenericGenerator(name = "uuid2",strategy = "org.hibernate.id.UUIDGenerator")// bular UUIDni to'liq xatosiz ishlashini ta'minlaydi
    private UUID id;

}
