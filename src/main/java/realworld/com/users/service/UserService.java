package realworld.com.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realworld.com.users.model.User;
import realworld.com.users.repository.UserRepository;
import realworld.com.users.repository.UserFollowerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserFollowerRepository followerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, 
                      UserFollowerRepository followerRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> getFollowees(Long userId) {
        return followerRepository.findFolloweesByUserId(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email, String password) {
        return userRepository.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void follow(Long userId, Long targetUserId) {
        followerRepository.follow(userId, targetUserId);
    }

    public void unfollow(Long userId, Long targetUserId) {
        followerRepository.unfollow(userId, targetUserId);
    }

    public boolean isFollowing(Long userId, Long targetUserId) {
        return followerRepository.isFollowing(userId, targetUserId);
    }

    public List<Long> getFollowingUserIds(Long userId, List<Long> targetUserIds) {
        return followerRepository.findFollowingUserIds(userId, targetUserIds);
    }
}