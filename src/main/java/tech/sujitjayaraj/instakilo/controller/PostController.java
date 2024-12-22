package tech.sujitjayaraj.instakilo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tech.sujitjayaraj.instakilo.dto.CommentDto;
import tech.sujitjayaraj.instakilo.dto.PostDto;
import tech.sujitjayaraj.instakilo.entity.Comment;
import tech.sujitjayaraj.instakilo.entity.Post;
import tech.sujitjayaraj.instakilo.entity.UserCredentials;
import tech.sujitjayaraj.instakilo.service.PostService;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid Post post, @AuthenticationPrincipal UserCredentials userCredentials, UriComponentsBuilder uriComponentsBuilder) {
        post.setUser(userCredentials.getUser());
        post = postService.createPost(post);

        URI locationURI = uriComponentsBuilder.path("/posts/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(locationURI).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostDto(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserCredentials userCredentials) {
        Post post = postService.getPost(id);

        if (!post.getUser().equals(userCredentials.getUser())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        postService.deletePost(id);

        return ResponseEntity.ok("Post with id " + id + " deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody @Valid Post post, @AuthenticationPrincipal UserCredentials userCredentials) {
        Post existingPost = postService.getPost(id);

        if (!existingPost.getUser().equals(userCredentials.getUser())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        post.setId(id);
        post.setUser(userCredentials.getUser());
        postService.createPost(post);

        return ResponseEntity.ok("Post with id " + id + " updated");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long id, @AuthenticationPrincipal UserCredentials userCredentials) {
        Post post = postService.getPost(id);
        post.getLikes().add(userCredentials.getUser());
        postService.createPost(post);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id, @AuthenticationPrincipal UserCredentials userCredentials) {
        Post post = postService.getPost(id);
        post.getLikes().remove(userCredentials.getUser());
        postService.createPost(post);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Set<CommentDto>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<String> addComment(@PathVariable Long id, @RequestBody @Valid Comment comment, @AuthenticationPrincipal UserCredentials userCredentials) {
        comment.setUser(userCredentials.getUser());
        postService.addComment(id, comment);

        return ResponseEntity.ok("Comment added to post with id " + id);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
