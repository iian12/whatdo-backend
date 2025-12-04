package com.meetup.whatdo.user.application.mapper;

import com.meetup.whatdo.user.domain.UserEntity;
import com.meetup.whatdo.user.domain.Users;

public class UserMapper {

    public static Users toDomain(UserEntity entity) {
        if (entity == null) return null;

        return Users.builder()
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .passwordHash(entity.getPasswordHash())
                .profileImageUrl(entity.getProfileImageUrl())
                .bio(entity.getBio())
                .accountStatus(entity.getAccountStatus())
                .role(entity.getRole())
                .provider(entity.getProvider())
                .subjectId(entity.getSubjectId())
                .build();
    }

    public static UserEntity toEntity(Users domain) {
        if (domain == null) return null;

        return UserEntity.fromDomain(domain);
    }
}
