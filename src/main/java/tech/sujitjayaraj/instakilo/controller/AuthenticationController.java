package tech.sujitjayaraj.instakilo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tech.sujitjayaraj.instakilo.dto.LoginResponse;
import tech.sujitjayaraj.instakilo.dto.LoginUserDto;
import tech.sujitjayaraj.instakilo.dto.RegisterUserDto;
import tech.sujitjayaraj.instakilo.entity.UserCredentials;
import tech.sujitjayaraj.instakilo.service.AuthenticationService;
import tech.sujitjayaraj.instakilo.service.JwtService;

import java.net.URI;

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

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserDto registerUserDto, UriComponentsBuilder uriComponentsBuilder) {
        UserCredentials userCredentials = authenticationService.register(registerUserDto);

        URI locationURI = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(userCredentials.getUser().getId())
                .toUri();

        return ResponseEntity.created(locationURI).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        UserCredentials authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
