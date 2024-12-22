package tech.sujitjayaraj.instakilo.dto;

import lombok.Data;
import tech.sujitjayaraj.instakilo.entity.User;

import java.io.Serializable;

@Data
public class UserResponseDto implements Serializable {

    public UserResponseDto(User user) {
        id = user.getId();
        username = user.getUsername();
        gender = user.getGender();
        name = user.getName();
        description = user.getDescription();
    }

    private Long id;

    private String username;

    private User.Gender gender;

    private String name;

    private String description;

    private int followersCount;

    private int followingsCount;
}
