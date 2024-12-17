package tech.sujitjayaraj.instakilo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import tech.sujitjayaraj.instakilo.dto.UserResponseDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.repository.UserRepository;
import tech.sujitjayaraj.instakilo.service.DateTimeService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final DateTimeService dateTimeService;

    @Autowired
    public UserController(UserRepository userRepository, DateTimeService dateTimeService) {
        this.userRepository = userRepository;
        this.dateTimeService = dateTimeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setCreatedAt(dateTimeService.getLocalDateTime(user.getCreatedAt()));

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<UserResponseDto>> getFollowers(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        List<User> followers = user.getFollowers();
        List<UserResponseDto> userResponseDtos = followers.stream().map(follower -> {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(follower.getId());
            userResponseDto.setName(follower.getName());
            userResponseDto.setUsername(follower.getUsername());
            userResponseDto.setCreatedAt(dateTimeService.getLocalDateTime(follower.getCreatedAt()));

            return userResponseDto;
        }).toList();

        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping("/{id}/followings")
    public ResponseEntity<List<UserResponseDto>> getFollowings(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        List<User> followers = user.getFollowings();
        List<UserResponseDto> userResponseDtos = followers.stream().map(follower -> {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(follower.getId());
            userResponseDto.setName(follower.getName());
            userResponseDto.setUsername(follower.getUsername());
            userResponseDto.setCreatedAt(dateTimeService.getLocalDateTime(follower.getCreatedAt()));

            return userResponseDto;
        }).toList();

        return ResponseEntity.ok(userResponseDtos);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long id, @AuthenticationPrincipal User user) {
        User userToFollow = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        if (user.getId().equals(userToFollow.getId())) {
            throw new IllegalArgumentException("You can not follow yourself");
        }

        if (userToFollow.getFollowers().contains(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You are already following this user");
        }

        userToFollow.getFollowers().add(user);
        user.getFollowings().add(userToFollow);

        userRepository.save(userToFollow);
        userRepository.save(user);

        return ResponseEntity.ok("You are now following " + userToFollow.getName());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArguments(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
    }
}
