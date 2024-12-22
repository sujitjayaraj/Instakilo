package tech.sujitjayaraj.instakilo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import tech.sujitjayaraj.instakilo.dto.UserResponseDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.entity.UserCredentials;
import tech.sujitjayaraj.instakilo.service.UserService;

import java.util.Arrays;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        return ResponseEntity.ok(userService.getUserDto(user));
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long id, @AuthenticationPrincipal UserCredentials userCredentials) {
        if (id.equals(userCredentials.getUser().getId())) {
            return ResponseEntity.badRequest().body("You cannot follow yourself");
        }
        userService.followUser(userCredentials.getUser().getId(), id);

        return ResponseEntity.ok("User with id " + id + " followed");
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable Long id, @AuthenticationPrincipal UserCredentials userCredentials) {
        if (id.equals(userCredentials.getUser().getId())) {
            return ResponseEntity.badRequest().body("You cannot unfollow yourself");
        }
        userService.unfollowUser(userCredentials.getUser().getId(), id);

        return ResponseEntity.ok("User with id " + id + " unfollowed");
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<Set<UserResponseDto>> getFollowers(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFollowers(id));
    }

    @GetMapping("/{id}/followings")
    public ResponseEntity<Set<UserResponseDto>> getFollowings(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFollowings(id));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        System.out.println(Arrays.toString(ex.getStackTrace()));
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
