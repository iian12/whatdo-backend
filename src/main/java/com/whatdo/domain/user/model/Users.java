package com.whatdo.domain.user.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @Tsid
    private String id;

    private String email;

    private String nickname;

    private String profileImgUrl;

    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String subjectId;

    @Builder
    public Users(String email, String nickname, String profileImgUrl, String description, Role role,
        Provider provider, String subjectId) {
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.description = description;
        this.role = role;
        this.provider = provider;
        this.subjectId = subjectId;
    }
}
