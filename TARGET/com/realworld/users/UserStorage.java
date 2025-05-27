package com.realworld.users;

import com.realworld.core.User;
import com.realworld.profile.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface UserStorage extends JpaRepository<User, Long> {
    CompletableFuture<List<User>> findAll();
    
    CompletableFuture<List<User>> findAllByIdIn(List<Long> userIds);
    
    CompletableFuture<Optional<User>> findById(Long userId);
    
    @Query("SELECT u FROM User u JOIN UserFollower f ON f.followeeId = u.id WHERE f.userId = :userId")
    CompletableFuture<List<User>> findFollowees(@Param("userId") Long userId);
    
    CompletableFuture<Optional<User>> findByUsername(String username);
    
    CompletableFuture<User> save(User user);
    
    CompletableFuture<Optional<User>> findByEmailAndPassword(String email, String password);
    
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM UserFollower f WHERE f.userId = :userId AND f.followeeId = :targetUserId")
    CompletableFuture<Boolean> isFollowing(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);
    
    @Query("SELECT f.followeeId FROM UserFollower f WHERE f.userId = :userId AND f.followeeId IN :targetUserIds")
    CompletableFuture<List<Long>> findFollowingUsers(@Param("userId") Long userId, @Param("targetUserIds") List<Long> targetUserIds);
}

@Repository
public class JdbcUserStorage implements UserStorage {
    private final UserFollowerRepository followerRepository;
    
    public JdbcUserStorage(UserFollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }
    
    @Override
    public CompletableFuture<Integer> follow(Long userId, Long targetUserId) {
        UserFollower follower = new UserFollower(userId, targetUserId);
        return CompletableFuture.supplyAsync(() -> {
            followerRepository.save(follower);
            return 1;
        });
    }
    
    @Override
    public CompletableFuture<Integer> unfollow(Long userId, Long targetUserId) {
        return CompletableFuture.supplyAsync(() -> 
            followerRepository.deleteByUserIdAndFolloweeId(userId, targetUserId)
        );
    }
}