package com.meetup.whatdo.user.infrastructure;

import com.meetup.whatdo.user.domain.UserEntity;
import com.meetup.whatdo.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
