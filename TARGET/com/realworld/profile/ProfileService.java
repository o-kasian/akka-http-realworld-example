package com.realworld.profile;

import com.realworld.core.User;
import com.realworld.users.UserStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProfileService {
    private final UserStorage userStorage;

    public ProfileService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public CompletableFuture<Optional<ResponseProfile>> getProfile(Long userId, String username) {
        return CompletableFuture.supplyAsync(() -> 
            userStorage.getUserByUsername(username)
                .flatMap(user -> userStorage.isFollowing(userId, user.getId())
                    .map(isFollowing -> new ResponseProfile(
                        new Profile(user.getUsername(), user.getBio(), user.getImage(), isFollowing)
                    ))
                )
        );
    }

    @Transactional
    public CompletableFuture<Optional<ResponseProfile>> follow(Long userId, String username) {
        return CompletableFuture.supplyAsync(() -> 
            userStorage.getUserByUsername(username)
                .map(user -> {
                    userStorage.follow(userId, user.getId());
                    return new ResponseProfile(
                        new Profile(user.getUsername(), user.getBio(), user.getImage(), true)
                    );
                })
        );
    }

    @Transactional
    public CompletableFuture<Optional<ResponseProfile>> unfollow(Long userId, String username) {
        return CompletableFuture.supplyAsync(() -> 
            userStorage.getUserByUsername(username)
                .map(user -> {
                    userStorage.unfollow(userId, user.getId());
                    return new ResponseProfile(
                        new Profile(user.getUsername(), user.getBio(), user.getImage(), false)
                    );
                })
        );
    }

    public CompletableFuture<List<User>> getFollowees(Long userId) {
        return CompletableFuture.supplyAsync(() -> 
            userStorage.getFollowees(userId)
        );
    }
}