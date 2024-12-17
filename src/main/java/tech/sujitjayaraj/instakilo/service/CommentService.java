package tech.sujitjayaraj.instakilo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.sujitjayaraj.instakilo.dto.CommentDto;
import tech.sujitjayaraj.instakilo.entity.Comment;
import tech.sujitjayaraj.instakilo.entity.Post;
import tech.sujitjayaraj.instakilo.repository.CommentRepository;
import tech.sujitjayaraj.instakilo.repository.PostRepository;

@Service
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Comment save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostID()).orElseThrow();
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setDescription(commentDto.getMessage());

        return commentRepository.save(comment);
    }
}
