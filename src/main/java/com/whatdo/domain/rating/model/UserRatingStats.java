package com.whatdo.domain.rating.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRatingStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Long ratingCount;
    private Long totalRating;

    @Builder
    public UserRatingStats(String userId, Long totalRating) {
        this.userId = userId;
        this.ratingCount = 1L;
        this.totalRating = totalRating;
    }

    public Double getAverageRating() {
        return ratingCount == 0 ? 0 : (double) totalRating / ratingCount;
    }

    public void addRatingStats(Long rating) {
        ratingCount++;
        totalRating += rating;
    }
}
