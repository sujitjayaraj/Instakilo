package tech.sujitjayaraj.instakilo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.sujitjayaraj.instakilo.dto.LoginResponse;
import tech.sujitjayaraj.instakilo.dto.LoginUserDto;
import tech.sujitjayaraj.instakilo.dto.RegisterUserDto;
import tech.sujitjayaraj.instakilo.dto.UserResponseDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.service.AuthenticationService;
import tech.sujitjayaraj.instakilo.service.JwtService;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
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
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
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
