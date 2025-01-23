package com.whatdo.domain.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserDto {

    private String email;
    private String profileImgUrl;
    private String nickname;
    private String subjectId;

    @Builder
    public GoogleUserDto(String email, String profileImgUrl, String nickname, String subjectId) {
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
        this.subjectId = subjectId;
    }
}
