package com.whatdo.domain.meet.controller;

import com.whatdo.domain.meet.dto.CreateMeetReqDto;
import com.whatdo.domain.meet.service.MeetService;
import com.whatdo.global.security.jwt.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meet")
public class MeetController {

    private final MeetService meetService;

    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @PostMapping
    public ResponseEntity<String> addMeet(@RequestBody CreateMeetReqDto reqDto, HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);

        String meetId = meetService.addMeet(reqDto, token);

        return ResponseEntity.ok().body(meetId);
    }
}
