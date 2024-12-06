package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 4, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Size(max = 50)
    private String description;

    @Lob
    private byte[] profileImage;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> followers;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<User> followings;

    @OneToMany(targetEntity = Post.class, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Role {
        ADMIN, USER
    }

    public enum Gender {
        MALE, FEMALE
    }
}
