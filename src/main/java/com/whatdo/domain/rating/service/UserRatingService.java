package com.whatdo.domain.rating.service;

import com.whatdo.domain.rating.dto.UserRatingReqDto;

public interface UserRatingService {

    void saveUserRating(UserRatingReqDto reqDto, String token);
}
