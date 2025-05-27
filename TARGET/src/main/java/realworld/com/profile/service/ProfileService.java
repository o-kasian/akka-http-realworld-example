package realworld.com.profile.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import realworld.com.profile.dto.ProfileDto;
import realworld.com.users.model.User;
import realworld.com.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    
    public ProfileDto getProfile(String username, Long currentUserId) {
        return userRepository.findByUsername(username)
            .map(user -> ProfileDto.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .following(isFollowing(user.getId(), currentUserId))
                .build())
            .orElse(ProfileDto.builder()
                .username("")
                .bio(null)
                .image(null)
                .following(false)
                .build());
    }
    
    private boolean isFollowing(Long userId, Long currentUserId) {
        // This would need to be implemented with a proper follow repository
        // For now, returning false as a placeholder
        return false;
    }
}