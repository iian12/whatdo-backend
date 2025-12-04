package com.meetup.whatdo.user.domain;

import com.meetup.whatdo.user.domain.enums.AccountStatus;
import com.meetup.whatdo.user.domain.enums.Provider;
import com.meetup.whatdo.user.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Users {

    private final UserId userId;
    private final String phone;
    private final String email;
    private String nickname;
    private String passwordHash;
    private String profileImageUrl;
    private String bio;
    private AccountStatus accountStatus;
    private final Role role;
    private final Provider provider;
    private final String subjectId;

    @Builder
    public Users(String phone,
                String email,
                String nickname,
                String passwordHash,
                String profileImageUrl,
                String bio,
                AccountStatus accountStatus,
                Role role,
                Provider provider,
                String subjectId) {
        this.userId = UserId.newId();
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.accountStatus = accountStatus != null ? accountStatus : AccountStatus.PENDING;
        this.role = role != null ? role : Role.USER;
        this.provider = provider;
        this.subjectId = subjectId;
    }

    public void activate() {
        if(this.accountStatus == AccountStatus.PENDING) {
            this.accountStatus = AccountStatus.ACTIVE;
        }
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changePasswordHash(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
    }

    public void changeProfileImageUrl(String newProfileImageUrl) {
        this.profileImageUrl = newProfileImageUrl;
    }

    public void changeBio(String newBio) {
        this.bio = newBio;
    }

    public void changeAccountStatus(AccountStatus newAccountStatus) {
        this.accountStatus = newAccountStatus;
    }
}
