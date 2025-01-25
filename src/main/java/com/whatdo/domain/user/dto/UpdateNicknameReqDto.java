package com.whatdo.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNicknameReqDto {

    private String nickname;

    public UpdateNicknameReqDto(String nickname) {
        this.nickname = nickname;
    }
}
