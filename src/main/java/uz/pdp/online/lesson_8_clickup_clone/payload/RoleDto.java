package uz.pdp.online.lesson_8_clickup_clone.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleDto {

    @NotNull
    private String name;

    @NotNull
    private Integer id;
}
