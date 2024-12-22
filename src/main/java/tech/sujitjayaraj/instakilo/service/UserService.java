package tech.sujitjayaraj.instakilo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.sujitjayaraj.instakilo.dto.UserResponseDto;
import tech.sujitjayaraj.instakilo.entity.User;
import tech.sujitjayaraj.instakilo.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getUserDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto(user);
        userResponseDto.setFollowersCount(userRepository.countFollowers(user.getId()));

        return userResponseDto;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));
    }

    public void followUser(Long followerId, Long followedId) {
        User follower = getUser(followerId);
        User followed = getUser(followedId);

        followed.getFollowers().add(follower);

        userRepository.save(followed);
    }

    public void unfollowUser(Long followerId, Long followedId) {
        User follower = getUser(followerId);
        User followed = getUser(followedId);

        followed.getFollowers().remove(follower);

        userRepository.save(followed);
    }

    public Set<UserResponseDto> getFollowers(Long id) {
        return getUser(id).getFollowers().stream()
                .map(this::getUserDto)
                .collect(Collectors.toSet());
    }

    public Set<UserResponseDto> getFollowings(Long id) {
        return getUser(id).getFollowings().stream()
                .map(this::getUserDto)
                .collect(Collectors.toSet());
    }
}
