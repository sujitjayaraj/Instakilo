package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sujitjayaraj.instakilo.entity.Comment;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Set<Comment> findByPostId(Long postId);
}
