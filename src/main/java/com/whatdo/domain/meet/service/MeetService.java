package com.whatdo.domain.meet.service;

import com.whatdo.domain.meet.dto.CreateMeetReqDto;

public interface MeetService {

    String addMeet(CreateMeetReqDto reqDto, String token);
}
