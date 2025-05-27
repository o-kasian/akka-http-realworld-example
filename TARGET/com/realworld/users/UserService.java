package com.realworld.users;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final String secretKey;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository,
                      String secretKey,
                      PasswordEncoder passwordEncoder,
                      ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<Optional<ResponseUser>> getCurrentUser(Long userId) {
        return CompletableFuture.supplyAsync(() ->
            userRepository.findById(userId)
                .map(user -> new ResponseUser(new UserWithToken(
                    user.getUsername(),
                    user.getEmail(),
                    user.getBio(),
                    user.getImage(),
                    encodeToken(user.getId())
                )))
        );
    }

    @Transactional
    public CompletableFuture<Optional<ResponseUser>> updateUser(Long id, UserUpdate userUpdate) {
        return CompletableFuture.supplyAsync(() ->
            userRepository.findById(id)
                .map(user -> {
                    User updatedUser = userUpdate.merge(user);
                    User savedUser = userRepository.save(updatedUser);
                    return new ResponseUser(new UserWithToken(
                        savedUser.getUsername(),
                        savedUser.getEmail(),
                        savedUser.getBio(),
                        savedUser.getImage(),
                        encodeToken(savedUser.getId())
                    ));
                })
        );
    }

    @Transactional
    public CompletableFuture<ResponseUser> register(UserRegistration userRegistration) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRegistration.create();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return new ResponseUser(new UserWithToken(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getBio(),
                savedUser.getImage(),
                encodeToken(savedUser.getId())
            ));
        });
    }

    public CompletableFuture<Optional<ResponseUser>> login(String email, String password) {
        return CompletableFuture.supplyAsync(() ->
            userRepository.findByEmailAndPassword(
                email,
                passwordEncoder.encode(password)
            ).map(user -> new ResponseUser(new UserWithToken(
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getImage(),
                encodeToken(user.getId())
            )))
        );
    }

    private String encodeToken(Long userId) {
        try {
            AuthTokenContent content = new AuthTokenContent(userId);
            return JWT.create()
                .withClaim("content", objectMapper.writeValueAsString(content))
                .sign(Algorithm.HMAC256(secretKey));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create token", e);
        }
    }
}