package com.whatdo.domain.rating.service;

import com.whatdo.domain.rating.dto.UserRatingReqDto;
import com.whatdo.domain.rating.model.UserRating;
import com.whatdo.domain.rating.model.UserRatingStats;
import com.whatdo.domain.rating.repository.UserRatingRepository;
import com.whatdo.domain.rating.repository.UserRatingStatsRepository;
import com.whatdo.domain.user.model.Users;
import com.whatdo.domain.user.repository.UserRepository;
import com.whatdo.global.security.jwt.TokenUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRatingServiceImpl implements UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserRatingStatsRepository userRatingStatsRepository;
    private final UserRepository userRepository;

    public UserRatingServiceImpl(UserRatingRepository userRatingRepository,
        UserRatingStatsRepository userRatingStatsRepository, UserRepository userRepository) {
        this.userRatingRepository = userRatingRepository;
        this.userRatingStatsRepository = userRatingStatsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUserRating(UserRatingReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRatingRepository.findByUserIdAndTargetUserIdAndMeetId(user.getId(),
            reqDto.getTargetUserId(), reqDto.getMeetId()).isPresent()) {
            throw new IllegalArgumentException("User already rated in this meet");
        }

        Users targetUser = userRepository.findById(reqDto.getTargetUserId())
            .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        try {
            UserRating userRating = UserRating.builder()
                .userId(user.getId())
                .targetUserId(targetUser.getId())
                .rating(reqDto.getRating())
                .build();

            userRatingRepository.save(userRating);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User already rated in this meet");
        }

        UserRatingStats userRatingStats = userRatingStatsRepository
            .findByUserId(targetUser.getId())
            .orElseGet(() ->
                UserRatingStats.builder()
                    .userId(targetUser.getId())
                    .totalRating(reqDto.getRating())
                    .build());

        userRatingStatsRepository.save(userRatingStats);
    }
}
