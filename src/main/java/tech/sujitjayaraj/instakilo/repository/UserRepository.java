package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.sujitjayaraj.instakilo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
