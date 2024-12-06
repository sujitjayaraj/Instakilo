package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Size(max = 500)
    private String description;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments;

    private Instant createdAt;

    @PrePersist
    private void setCreated() {
        this.createdAt = Instant.now();
    }
}
