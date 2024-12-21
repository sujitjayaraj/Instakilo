package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sujitjayaraj.instakilo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
