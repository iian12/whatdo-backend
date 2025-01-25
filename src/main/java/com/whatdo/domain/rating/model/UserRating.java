package com.whatdo.domain.rating.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints = @UniqueConstraint(
        name = "unique_user_meet_rating",
        columnNames = {"userId", "targetUserId", "meetId"}
    )
)
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String targetUserId;
    private String meetId;

    private Long rating;

    @Builder
    public UserRating(String userId, String targetUserId, String meetId, Long rating) {
        this.userId = userId;
        this.targetUserId = targetUserId;
        this.meetId = meetId;
        this.rating = rating;
    }
}
