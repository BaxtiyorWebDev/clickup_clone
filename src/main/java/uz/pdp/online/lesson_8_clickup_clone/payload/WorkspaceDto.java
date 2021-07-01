package uz.pdp.online.lesson_8_clickup_clone.payload;

import lombok.Data;
import uz.pdp.online.lesson_8_clickup_clone.entity.Attachment;
import uz.pdp.online.lesson_8_clickup_clone.entity.User;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class WorkspaceDto {

    @NotNull
    private String name;

    @NotNull
    private String  color;

    private UUID avatarId;


}
