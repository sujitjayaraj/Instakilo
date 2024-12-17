package tech.sujitjayaraj.instakilo.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private String username;

    private String createdAt;
}
