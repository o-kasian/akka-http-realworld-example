package com.realworld.users;

import com.realworld.core.UserRegistration;
import com.realworld.core.UserUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/")
public class UserRoute {

    private final String secretKey;
    private final UserService usersService;

    public UserRoute(String secretKey, UserService usersService) {
        this.secretKey = secretKey;
        this.usersService = usersService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginPasswordUser loginRequest) {
        return usersService.login(loginRequest.getUser().getEmail(), loginRequest.getUser().getPassword())
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(usersService.register(registerRequest.getUser()));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@RequestAttribute("userId") String userId) {
        return usersService.getCurrentUser(userId)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestAttribute("userId") String userId, 
                                      @RequestBody UserUpdateParam updateParam) {
        return usersService.updateUser(userId, updateParam.getUser())
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RegisterRequest {
        private UserRegistration user;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class LoginPasswordUser {
        private LoginPassword user;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class LoginPassword {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserProfile {
        private String username;
        private String email;
        private String bio;
        private String image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserUpdateParam {
        private UserUpdate user;
    }
}