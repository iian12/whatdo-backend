package com.whatdo.domain.meet.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @Tsid
    private String id;

    private String name;

    private Long count;

    @Builder
    public Hashtag(String name) {
        this.name = name;
        this.count = 1L;
    }

    public void addCount() {
        count++;
    }
}
