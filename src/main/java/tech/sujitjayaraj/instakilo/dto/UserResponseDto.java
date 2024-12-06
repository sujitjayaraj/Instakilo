package tech.sujitjayaraj.instakilo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private String username;

    private String email;

    private LocalDateTime createdAt;
}
