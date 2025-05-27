package com.realworld.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

public class core {
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthTokenContent {
        private Long userId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthTokenContent2 {
        private String userId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "users")
    public static class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotEmpty(message = "username.empty")
        @Column(unique = true)
        private String username;

        @NotEmpty(message = "password.empty")
        @JsonIgnore
        private String password;

        @NotEmpty(message = "email.empty")
        @Column(unique = true)
        private String email;

        private String bio;

        private String image;

        private Timestamp createdAt;

        private Timestamp updatedAt;

        @PrePersist
        protected void onCreate() {
            createdAt = new Timestamp(new Date().getTime());
            updatedAt = new Timestamp(new Date().getTime());
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = new Timestamp(new Date().getTime());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseUser {
        private UserWithToken user;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserWithToken {
        private String username;
        private String email;
        private String bio;
        private String image;
        private String token;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserUpdate {
        private String username;
        private String password;
        private String email;
        private String bio;
        private String image;

        public User merge(User user) {
            if (username != null) user.setUsername(username);
            if (password != null) user.setPassword(PASSWORD_ENCODER.encode(password));
            if (email != null) user.setEmail(email);
            if (bio != null) user.setBio(bio);
            if (image != null) user.setImage(image);
            user.setUpdatedAt(new Timestamp(new Date().getTime()));
            return user;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRegistration {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;

        public User create() {
            return User.builder()
                    .username(username)
                    .password(PASSWORD_ENCODER.encode(password))
                    .email(email)
                    .createdAt(new Timestamp(new Date().getTime()))
                    .updatedAt(new Timestamp(new Date().getTime()))
                    .build();
        }
    }
}