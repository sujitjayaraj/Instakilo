package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public enum Role {
        USER, ADMIN
    }
}
