package com.whatdo.domain.rating.repository;

import com.whatdo.domain.rating.model.UserRating;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    Optional<UserRating> findByUserIdAndTargetUserIdAndMeetId(String userId, String targetUserId, String meetId);
}
