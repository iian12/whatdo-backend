package com.whatdo.domain.meet.repository;

import com.whatdo.domain.meet.model.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meet, String> {

}
