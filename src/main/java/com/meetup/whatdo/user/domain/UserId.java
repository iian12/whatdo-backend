package com.meetup.whatdo.user.domain;

import com.meetup.whatdo.common.utils.TsidCreator;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * User 식별자를 표현하는 Value Object.
 * 원시 타입 전달 오류 방지
 * 도메인 계층에서의 명확한 ID 개념 확보
 * @author Minchan Park
 * @version 1.0
 */
@Embeddable
public record UserId(Long value) implements Serializable {
    public static UserId of(Long value) {
        return new UserId(value);
    }

    /**
     * Tsid를 이용해 새로운 UserId 생성
     * @return 새로운 UserId 객체
     */
    public static UserId newId() {
        return new UserId(TsidCreator.create());
    }


}
