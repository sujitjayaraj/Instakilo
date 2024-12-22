package tech.sujitjayaraj.instakilo.dto;

import lombok.Data;
import tech.sujitjayaraj.instakilo.entity.Comment;

import java.time.Instant;

@Data
public class CommentDto {

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.user = new UserPostDto(comment.getUser());
        this.createdAt = comment.getCreatedAt();
        this.editedAt = comment.getEditedAt();
    }

    private Long id;

    private String description;

    private UserPostDto user;

    private Instant createdAt;

    private Instant editedAt;


}
