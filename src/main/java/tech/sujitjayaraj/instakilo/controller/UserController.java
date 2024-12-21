package tech.sujitjayaraj.instakilo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        return ResponseEntity.ok(user);
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
    @JsonProperty("followers")
    public ResponseEntity<Set<User>> getFollowers(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFollowers(id));
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
