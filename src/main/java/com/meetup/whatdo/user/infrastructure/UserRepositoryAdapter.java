package com.meetup.whatdo.user.infrastructure;

import com.meetup.whatdo.user.domain.UserEntity;
import com.meetup.whatdo.user.domain.UserId;
import com.meetup.whatdo.user.application.mapper.UserMapper;
import com.meetup.whatdo.user.domain.Users;
import com.meetup.whatdo.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return springDataUserRepository
                .findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<Users> findById(UserId userId) {
        return springDataUserRepository
                .findById(userId.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Users save(Users user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = springDataUserRepository.save(entity);
        return UserMapper.toDomain(saved);
    }
}
