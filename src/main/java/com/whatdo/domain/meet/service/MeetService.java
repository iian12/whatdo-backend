package com.whatdo.domain.meet.service;

import com.whatdo.domain.meet.dto.CreateMeetReqDto;
import com.whatdo.domain.meet.dto.MeetListResDto;
import java.util.List;

public interface MeetService {

    String addMeet(CreateMeetReqDto reqDto, String token);

    List<MeetListResDto> getMeetList();
}
