package com.whatdo.domain.meet.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MeetListResDto {

    private String meetId;
    private String title;
    private String hostId;
    private List<HashtagDto> hashtags;
    private String hostNickname;
    private int maxParticipants;
    private int participants;
    private boolean isOpen;
    private LocalDateTime createdAt;

    @Builder
    public MeetListResDto(String meetId, String title, String hostId, List<HashtagDto> hashtags, String hostNickname, int maxParticipants, int participants, boolean isOpen, LocalDateTime createdAt) {
        this.meetId = meetId;
        this.title = title;
        this.hostId = hostId;
        this.hashtags = hashtags;
        this.hostNickname = hostNickname;
        this.maxParticipants = maxParticipants;
        this.participants = participants;
        this.isOpen = isOpen;
        this.createdAt = createdAt;
    }
}
