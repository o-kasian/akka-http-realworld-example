package realworld.com.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import realworld.com.users.User;
import java.util.Optional;

public interface UserFollowerRepository extends JpaRepository<UserFollower, UserFollowerId> {
    
    Optional<UserFollower> findByUserAndFollowee(User user, User followee);
    
    boolean existsByUserAndFollowee(User user, User followee);
}