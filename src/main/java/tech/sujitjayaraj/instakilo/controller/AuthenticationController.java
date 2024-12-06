package tech.sujitjayaraj.instakilo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.sujitjayaraj.instakilo.dto.LoginUserDto;
import tech.sujitjayaraj.instakilo.dto.RegisterUserDto;
import tech.sujitjayaraj.instakilo.dto.UserResponseDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.service.AuthenticationService;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid RegisterUserDto registerUserDto) {
        User user = authenticationService.signup(registerUserDto);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setCreatedAt(LocalDateTime.now());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDto loginUserDto) {
        User user = authenticationService.authenticate(loginUserDto);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(userResponseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
