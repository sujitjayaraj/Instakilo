package tech.sujitjayaraj.instakilo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.sujitjayaraj.instakilo.dto.CreatePostDto;
import tech.sujitjayaraj.instakilo.entity.Post;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.service.PostService;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody @Valid CreatePostDto createPostDto, @AuthenticationPrincipal User user) {
        try {
            Post post = postService.save(createPostDto, user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/posts/{id}")
                    .buildAndExpand(post.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id)  {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid CreatePostDto createPostDto) {
        try {
            Post post = postService.update(id, createPostDto);
            return ResponseEntity.ok(post);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
