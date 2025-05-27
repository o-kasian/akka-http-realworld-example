package com.realworld.profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "followers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowersTable {
    
    @Id
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "followee_id")
    private Long followeeId;
    
    @CreationTimestamp
    @Column(name = "inserted_at")
    private Timestamp insertedAt;
}