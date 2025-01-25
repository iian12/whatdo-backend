package com.whatdo.domain.rating.controller;

import com.whatdo.domain.rating.dto.UserRatingReqDto;
import com.whatdo.domain.rating.service.UserRatingService;
import com.whatdo.global.security.jwt.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-rating")
public class UserRatingController {

    private final UserRatingService userRatingService;

    public UserRatingController(UserRatingService userRatingService) {
        this.userRatingService = userRatingService;
    }

    @PostMapping
    public ResponseEntity<String> saveUserRating(@RequestBody UserRatingReqDto reqDto,
        HttpServletRequest request) {

        String token = TokenUtils.extractTokenFromRequest(request);

        userRatingService.saveUserRating(reqDto, token);

        return ResponseEntity.ok("Rating saved");
    }
}
