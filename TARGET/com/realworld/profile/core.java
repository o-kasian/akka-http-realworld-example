package com.realworld.profile;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProfile {
    private Profile profile;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollower {
    private Long userId;
    private Long followeeId;
    private Timestamp insertedAt;
}