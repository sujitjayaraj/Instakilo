package tech.sujitjayaraj.instakilo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.sujitjayaraj.instakilo.dto.LoginUserDto;
import tech.sujitjayaraj.instakilo.dto.RegisterUserDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto registerUserDto) {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new IllegalArgumentException("Username " + registerUserDto.getUsername() + " is already taken");
        } else if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new IllegalArgumentException("Email " + registerUserDto.getEmail() + " is already registered");
        }

        User user = new User();
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setEmail(registerUserDto.getEmail());
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto) throws UsernameNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByUsername(loginUserDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + loginUserDto.getUsername() + " not found"));
    }
}
