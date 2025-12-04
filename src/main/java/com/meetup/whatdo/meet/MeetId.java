package com.meetup.whatdo.meet;

import com.meetup.whatdo.common.utils.TsidCreator;
import jakarta.persistence.Embeddable;

@Embeddable
public record MeetId(Long value) {

    public static MeetId of(Long value) {
        return new MeetId(value);
    }

    public static MeetId newId() {
        return new MeetId(TsidCreator.create());
    }
}
