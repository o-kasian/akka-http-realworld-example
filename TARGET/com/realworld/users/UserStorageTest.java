package com.realworld.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserStorageTest {

    @Autowired
    private UserStorage userStorage;

    @AfterEach
    void cleanup() {
        // DatabaseCleaner will be handled by Spring's transaction management
    }

    @Test
    void getUserByUsername_ShouldReturnProfileById() {
        TestUser testUser1 = new TestUser(1L, "username-1", "username-email-1", "user-password-1");
        TestUser testUser2 = new TestUser(2L, "username-2", "username-email-2", "user-password-2");

        userStorage.getUsers();
        User savedUser1 = userStorage.saveUser(createUser(testUser1));
        User savedUser2 = userStorage.saveUser(createUser(testUser2));
        
        Optional<User> maybeProfile = userStorage.getUserByUsername(testUser2.username());
        
        assertThat(maybeProfile).isPresent();
        assertThat(maybeProfile.get()).isEqualTo(savedUser2);
    }

    @Test
    void follow_ShouldSucceed() {
        TestUser testUser1 = new TestUser(1L, "username-1", "username-email-1", "user-password-1");
        TestUser testUser2 = new TestUser(2L, "username-2", "username-email-2", "user-password-2");

        User userA = userStorage.saveUser(createUser(testUser1));
        User userB = userStorage.saveUser(createUser(testUser2));
        
        int successFlag = userStorage.follow(userA.getId(), userB.getId());
        
        assertThat(successFlag).isEqualTo(1);
    }

    @Test
    void isFollowing_ShouldReturnTrue_WhenUserFollows() {
        TestUser testUser1 = new TestUser(1L, "username-1", "username-email-1", "user-password-1");
        TestUser testUser2 = new TestUser(2L, "username-2", "username-email-2", "user-password-2");

        User userA = userStorage.saveUser(createUser(testUser1));
        User userB = userStorage.saveUser(createUser(testUser2));
        userStorage.follow(userA.getId(), userB.getId());
        
        boolean isFollowing = userStorage.isFollowing(userA.getId(), userB.getId());
        
        assertThat(isFollowing).isTrue();
    }

    @Test
    void isFollowing_ShouldReturnFalse_WhenUserDoesNotFollow() {
        TestUser testUser1 = new TestUser(1L, "username-1", "username-email-1", "user-password-1");
        TestUser testUser2 = new TestUser(2L, "username-2", "username-email-2", "user-password-2");

        User userA = userStorage.saveUser(createUser(testUser1));
        User userB = userStorage.saveUser(createUser(testUser2));
        userStorage.follow(userA.getId(), userB.getId());
        
        boolean isFollowing = userStorage.isFollowing(userB.getId(), userA.getId());
        
        assertThat(isFollowing).isFalse();
    }

    private User createUser(TestUser testUser) {
        Timestamp currentTime = new Timestamp(new Date().getTime());
        return new User(
            testUser.userId(),
            testUser.username(),
            testUser.password(),
            testUser.email(),
            null,
            null,
            currentTime,
            currentTime
        );
    }

    private record TestUser(
        Long userId,
        String username,
        String email,
        String password
    ) {}
}