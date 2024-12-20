package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(
            name="likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

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
