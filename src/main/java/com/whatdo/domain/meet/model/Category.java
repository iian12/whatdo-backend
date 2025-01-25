package com.whatdo.domain.meet.model;

public enum Category {

    EXERCISE,                                       // 운동
    SPORTS,                                         // 스포츠
    ART_AND_CREATION,                               // 예술과 창작
    GAMES,                                          // 게임
    MUSIC,                                          // 음악
    TRAVEL,                                         // 여행
    TECHNOLOGY_LEARNING,                            // 기술과 학습
    BUSINESS_AND_SELF_DEVELOPMENT,                  // 비즈니스와 자기계발
    OTHERS;


    public static Category fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }

        try {
            // Enum 이름과 동일한 경우 직접 변환
            return Category.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid MainInterest value: " + input);
        }
    }
}
