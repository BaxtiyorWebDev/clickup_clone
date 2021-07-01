package uz.pdp.online.lesson_8_clickup_clone.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
