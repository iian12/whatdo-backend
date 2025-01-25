package com.whatdo.domain.meet.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meet {

    @Id
    @Tsid
    private String id;

    private String title;

    private String description;

    @ElementCollection
    private List<String> hashtagIds;

    private Category category;

    private String hostId;

    private int maxParticipants;

    @ElementCollection
    private List<String> participantIds;

    private boolean isOpen;

    private LocalDateTime createdAt;

    @Builder
    public Meet(String title, String description, Category category, List<String> hashtagIds, String hostId, int maxParticipants, boolean isOpen) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.hostId = hostId;
        this.hashtagIds = hashtagIds;
        this.maxParticipants = maxParticipants;
        this.participantIds = new ArrayList<>();
        this.isOpen = isOpen;
        this.createdAt = LocalDateTime.now();
    }

    public void addParticipant(String participantId) {
        this.participantIds.add(participantId);
    }

    public void updateMeet(String title, String description, Category category, List<String> hashtagIds, int maxParticipants, boolean isOpen) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.hashtagIds = hashtagIds;
        this.maxParticipants = maxParticipants;
        this.isOpen = isOpen;
    }

    public void removeParticipant(String participantId) {
        this.participantIds.remove(participantId);
    }

    public boolean isFull() {
        return this.participantIds.size() >= this.maxParticipants;
    }

    public boolean isParticipant(String participantId) {
        return this.participantIds.contains(participantId);
    }

    public void updateIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void updateMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}
