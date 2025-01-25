package com.whatdo.domain.meet.repository;

import com.whatdo.domain.meet.model.Hashtag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, String> {
    Optional<Hashtag> findByName(String name);
}
