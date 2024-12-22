package tech.sujitjayaraj.instakilo.dto;

import lombok.Data;
import tech.sujitjayaraj.instakilo.entity.Post;

import java.time.Instant;

@Data
public class PostDto {

    public PostDto(Post post) {
        id = post.getId();
        user = new UserPostDto(post.getUser());
        description = post.getDescription();
        createdAt = post.getCreatedAt();
        editedAt = post.getEditedAt();
    }

    private Long id;

    private UserPostDto user;

    private String description;

    private Instant createdAt;

    private Instant editedAt;

    private int likesCount;

    private int commentsCount;
}
