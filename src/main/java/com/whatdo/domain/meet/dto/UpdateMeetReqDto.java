package com.whatdo.domain.meet.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMeetReqDto {

    private String title;
    private String description;
    private String categoryName;
    private List<String> hashtagNames;
    private int maxParticipants;
    private boolean isOpen;

    public UpdateMeetReqDto(String title, String description, String categoryName, List<String> hashtagNames, int maxParticipants, boolean isOpen) {
        this.title = title;
        this.description = description;
        this.categoryName = categoryName;
        this.hashtagNames = hashtagNames;
        this.maxParticipants = maxParticipants;
        this.isOpen = isOpen;
    }
}
