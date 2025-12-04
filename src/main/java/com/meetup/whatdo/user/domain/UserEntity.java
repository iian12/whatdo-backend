package com.meetup.whatdo.user.domain;

import com.meetup.whatdo.user.domain.enums.AccountStatus;
import com.meetup.whatdo.user.domain.enums.Provider;
import com.meetup.whatdo.user.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA 사용자 엔티티 클래스
 * @version 1.0
 * @author Minchan Park
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    private Long userId;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 50)
    private String nickname;

    @Column
    private String passwordHash;

    @Column
    private String profileImageUrl;

    @Column
    private String bio;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(unique = true)
    private String subjectId;

    public UserEntity(Long userId,
                      String phone,
                      String email,
                      String nickname,
                      String passwordHash,
                      String profileImageUrl,
                      String bio,
                      AccountStatus accountStatus,
                      Role role,
                      Provider provider,
                      String subjectId) {
        this.userId = userId;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.accountStatus = accountStatus;
        this.role = role;
        this.provider = provider;
        this.subjectId = subjectId;
    }

    // 도메인 → 엔티티 변환용 팩토리 메서드
    public static UserEntity fromDomain(Users domain) {
        return new UserEntity(
                domain.getUserId().value(),
                domain.getPhone(),
                domain.getEmail(),
                domain.getNickname(),
                domain.getPasswordHash(),
                domain.getProfileImageUrl(),
                domain.getBio(),
                domain.getAccountStatus(),
                domain.getRole(),
                domain.getProvider(),
                domain.getSubjectId()
        );
    }

}
