package tech.sujitjayaraj.instakilo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.sujitjayaraj.instakilo.dto.CreatePostDto;
import tech.sujitjayaraj.instakilo.entity.Image;
import tech.sujitjayaraj.instakilo.entity.Post;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final ImageService imageService;

    @Autowired
    public PostService(PostRepository postRepository, ImageService imageService) {
        this.postRepository = postRepository;
        this.imageService = imageService;
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(CreatePostDto createPostDto, User user) {
        Post post = new Post();
        post.setUser(user);
        post.setDescription(createPostDto.getDescription());

        return postRepository.save(post);
    }

    public Post update(Long id, CreatePostDto createPostDto) {
        Post post = postRepository.findById(id).orElseThrow();

        if (createPostDto.getDescription() != null) {
            post.setDescription(createPostDto.getDescription());
        }

        return postRepository.save(post);
    }
}
