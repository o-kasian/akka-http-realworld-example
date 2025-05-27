package com.realworld.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/profiles")
public class ProfileRoute {

    private final ProfileService profileService;

    public ProfileRoute(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal Long userId,
                                      @PathVariable String username) {
        return profileService.getProfile(userId, username)
                .map(profile -> ResponseEntity.ok(profile))
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<?> follow(@AuthenticationPrincipal Long userId,
                                  @PathVariable String username) {
        return ResponseEntity.ok(profileService.follow(userId, username));
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<?> unfollow(@AuthenticationPrincipal Long userId,
                                    @PathVariable String username) {
        return ResponseEntity.ok(profileService.unfollow(userId, username));
    }
}