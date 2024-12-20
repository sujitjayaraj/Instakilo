package tech.sujitjayaraj.instakilo.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.sujitjayaraj.instakilo.dto.LoginUserDto;
import tech.sujitjayaraj.instakilo.dto.RegisterUserDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.entity.UserCredentials;
import tech.sujitjayaraj.instakilo.repository.UserCredentialsRepository;

@Service
public class AuthenticationService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserCredentialsRepository userCredentialsRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserCredentials register(@Valid RegisterUserDto registerUserDto) {
        UserCredentials userCredentials = new UserCredentials();
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setGender(registerUserDto.getGender());
        userCredentials.setEmail(registerUserDto.getEmail());
        userCredentials.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        userCredentials.setUser(user);

        return userCredentialsRepository.save(userCredentials);
    }

    public UserCredentials authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userCredentialsRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + loginUserDto.getEmail() + " not found"));
    }
}
