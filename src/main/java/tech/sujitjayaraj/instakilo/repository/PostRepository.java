package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.sujitjayaraj.instakilo.entity.Post;
import tech.sujitjayaraj.instakilo.entity.User;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT COUNT(u) FROM User u JOIN u.likes WHERE u.id = :id")
    int countLikesById(Long id);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :id")
    int countCommentsById(Long id);
}
