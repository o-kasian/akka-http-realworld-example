package realworld.com.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import realworld.com.users.model.*;
import realworld.com.users.service.UserService;
import realworld.com.security.JwtTokenProvider;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/users/login")
    public ResponseEntity<ResponseUser> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.findUserByEmail(loginRequest.getEmail(), loginRequest.getPassword())
            .map(user -> {
                String token = jwtTokenProvider.createToken(user.getId());
                UserWithToken userWithToken = new UserWithToken(
                    user.getUsername(),
                    user.getEmail(),
                    user.getBio(),
                    user.getImage(),
                    token
                );
                return ResponseEntity.ok(new ResponseUser(userWithToken));
            })
            .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserRegistration registration = registerRequest.getUser();
        User user = registration.toUser(userService.getPasswordEncoder());
        user = userService.register(user);
        
        String token = jwtTokenProvider.createToken(user.getId());
        UserWithToken userWithToken = new UserWithToken(
            user.getUsername(),
            user.getEmail(),
            user.getBio(),
            user.getImage(),
            token
        );
        return ResponseEntity.ok(new ResponseUser(userWithToken));
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseUser> getCurrentUser(@AuthenticationPrincipal Long userId) {
        return userService.getUser(userId)
            .map(user -> {
                String token = jwtTokenProvider.createToken(user.getId());
                UserWithToken userWithToken = new UserWithToken(
                    user.getUsername(),
                    user.getEmail(),
                    user.getBio(),
                    user.getImage(),
                    token
                );
                return ResponseEntity.ok(new ResponseUser(userWithToken));
            })
            .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/user")
    public ResponseEntity<ResponseUser> updateUser(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody UpdateUserRequest updateRequest) {
        return userService.getUser(userId)
            .map(user -> {
                User updatedUser = updateRequest.getUser().merge(user, userService.getPasswordEncoder());
                updatedUser = userService.saveUser(updatedUser);
                
                String token = jwtTokenProvider.createToken(updatedUser.getId());
                UserWithToken userWithToken = new UserWithToken(
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getBio(),
                    updatedUser.getImage(),
                    token
                );
                return ResponseEntity.ok(new ResponseUser(userWithToken));
            })
            .orElse(ResponseEntity.badRequest().build());
    }
}