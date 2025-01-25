package com.whatdo.domain.rating.repository;

import com.whatdo.domain.rating.model.UserRatingStats;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingStatsRepository extends JpaRepository<UserRatingStats, Long> {
    Optional<UserRatingStats> findByUserId(String userId);
}
