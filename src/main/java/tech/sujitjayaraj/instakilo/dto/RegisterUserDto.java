package tech.sujitjayaraj.instakilo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @Size(min = 5)
    private String password;
}
