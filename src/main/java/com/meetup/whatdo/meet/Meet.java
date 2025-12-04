package com.meetup.whatdo.meet;

import com.meetup.whatdo.user.domain.UserId;
import com.meetup.whatdo.user.infrastructure.converter.UserIdConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meet {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "meet_id"))
    private MeetId meetId;

    private String title;
    private String description;

    @ElementCollection
    @CollectionTable(name = "meeting_participants_ids",
                    joinColumns = @JoinColumn(name = "meet_id")
    )
    @Column(name = "user_id")
    @Convert(converter = UserIdConverter.class)
    private List<UserId> participantIds = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime date;


}
