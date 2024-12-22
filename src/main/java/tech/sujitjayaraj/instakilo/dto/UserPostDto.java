package tech.sujitjayaraj.instakilo.dto;

import lombok.Data;
import tech.sujitjayaraj.instakilo.entity.User;

@Data
public class UserPostDto {

    public UserPostDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
    }

    private Long id;

    private String username;

    private String name;
}
