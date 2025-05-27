package realworld.com.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import realworld.com.users.model.User;
import realworld.com.users.model.UserFollower;

import java.util.List;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
    @Query("SELECT u FROM User u JOIN UserFollower f ON u.id = f.followeeId WHERE f.userId = :userId")
    List<User> findFolloweesByUserId(Long userId);

    @Modifying
    @Query(value = "INSERT INTO user_followers (user_id, followee_id, created_at) VALUES (:userId, :followeeId, CURRENT_TIMESTAMP)", nativeQuery = true)
    void follow(Long userId, Long followeeId);

    @Modifying
    @Query("DELETE FROM UserFollower f WHERE f.userId = :userId AND f.followeeId = :followeeId")
    void unfollow(Long userId, Long followeeId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM UserFollower f WHERE f.userId = :userId AND f.followeeId = :followeeId")
    boolean isFollowing(Long userId, Long followeeId);

    @Query("SELECT f.followeeId FROM UserFollower f WHERE f.userId = :userId AND f.followeeId IN :targetUserIds")
    List<Long> findFollowingUserIds(Long userId, List<Long> targetUserIds);
}