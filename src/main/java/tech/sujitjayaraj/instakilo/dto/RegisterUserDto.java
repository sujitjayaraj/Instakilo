package tech.sujitjayaraj.instakilo.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tech.sujitjayaraj.instakilo.entity.User;

@Data
public class RegisterUserDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private User.Gender gender;
}
