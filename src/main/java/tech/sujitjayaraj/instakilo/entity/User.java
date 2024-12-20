package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String description;

    private Gender gender;

    @CreationTimestamp
    private Instant createdAt;

    @OneToOne(mappedBy = "user")
    private UserCredentials credentials;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name="followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name="followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> followings = new HashSet<>();

    @OneToMany(targetEntity = Post.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts;

    @ManyToMany(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinTable(
            name="likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likes = new HashSet<>();

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    public enum Gender {
        MALE, FEMALE
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
