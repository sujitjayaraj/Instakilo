package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@Entity
public class UserCredentials implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private boolean locked = false;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
