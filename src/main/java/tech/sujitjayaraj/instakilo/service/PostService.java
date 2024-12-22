package tech.sujitjayaraj.instakilo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.sujitjayaraj.instakilo.dto.CommentDto;
import tech.sujitjayaraj.instakilo.dto.PostDto;
import tech.sujitjayaraj.instakilo.entity.Comment;
import tech.sujitjayaraj.instakilo.entity.Post;
import tech.sujitjayaraj.instakilo.repository.CommentRepository;
import tech.sujitjayaraj.instakilo.repository.PostRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Post with id " + id + " not found"));
    }

    public PostDto getPostDto(Long id) {
        PostDto postDto = new PostDto(getPost(id));
        postDto.setCommentsCount(postRepository.countCommentsById(id));
        postDto.setLikesCount(postRepository.countLikesById(id));

        return postDto;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void addComment(Long id, Comment comment) {
        Post post = getPost(id);
        comment.setPost(post);
        commentRepository.save(comment);
    }


    public Set<CommentDto> getComments(Long id) {
        Post post = getPost(id);

        return post.getComments().stream()
                .map(CommentDto::new)
                .collect(Collectors.toSet());
    }
}
