package com.whatdo.domain.rating.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRatingReqDto {

    private String targetUserId;
    private String meetId;
    private Long rating;

    public UserRatingReqDto(String targetUserId, String meetId, Long rating) {
        this.targetUserId = targetUserId;
        this.meetId = meetId;
        this.rating = rating;
    }
}
