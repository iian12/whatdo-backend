package com.whatdo.domain.meet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagDto {

    private String hashtagId;
    private String hashtagName;

    public HashtagDto(String hashtagId, String hashtagName) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }
}
