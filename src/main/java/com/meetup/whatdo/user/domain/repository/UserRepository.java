package com.meetup.whatdo.user.domain.repository;

import com.meetup.whatdo.user.domain.UserId;
import com.meetup.whatdo.user.domain.Users;

import java.util.Optional;

public interface UserRepository {
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(UserId userId);
    Users save(Users user);
}
