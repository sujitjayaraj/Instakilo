package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sujitjayaraj.instakilo.entity.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserById(Long id);

    @Query("SELECT u FROM User u JOIN u.followers WHERE u.id = :id")
    Set<User> findFollowersById(@Param("id") Long id);

    @Query("SELECT COUNT(f) FROM User u JOIN u.followers f WHERE u.id = :id")
    int countFollowers(@Param("id") Long id);

    @Query("SELECT COUNT(f) FROM User u JOIN u.followings f WHERE u.id = :id")
    int countFollowings(@Param("id") Long id);
}
