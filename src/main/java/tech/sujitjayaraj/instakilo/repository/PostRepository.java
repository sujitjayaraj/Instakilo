package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sujitjayaraj.instakilo.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
