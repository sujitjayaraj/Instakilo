package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Post.class)
    private Post post;

    private String description;

    private Instant createdAt;

    private Instant editedAt;

    @PrePersist
    void setCreated() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    void setEdited() {
        this.editedAt = Instant.now();
    }
}
