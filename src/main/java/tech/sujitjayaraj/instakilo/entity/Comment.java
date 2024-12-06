package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Post.class)
    private Post post;

    @NotBlank
    @Size(max = 500)
    private String description;

    private Instant createdAt;

    private Instant editedAt;

    @PrePersist
    private void setCreated() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    private void setEdited() {
        this.editedAt = Instant.now();
    }
}
