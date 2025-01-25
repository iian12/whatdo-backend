package com.whatdo.domain.meet.service;

import com.whatdo.domain.meet.dto.CreateMeetReqDto;
import com.whatdo.domain.meet.model.Hashtag;
import com.whatdo.domain.meet.model.Meet;
import com.whatdo.domain.meet.repository.HashtagRepository;
import com.whatdo.domain.meet.repository.MeetRepository;
import com.whatdo.domain.meet.model.Category;
import com.whatdo.domain.user.model.Users;
import com.whatdo.domain.user.repository.UserRepository;
import com.whatdo.global.security.jwt.TokenUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeetServiceImpl implements MeetService {

    private final MeetRepository meetRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    public MeetServiceImpl(MeetRepository meetRepository, UserRepository userRepository,
        HashtagRepository hashtagRepository) {
        this.meetRepository = meetRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    public String addMeet(CreateMeetReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (reqDto.getHashtagNames().size() > 3) {
            throw new IllegalArgumentException("Too many Hashtags");
        }

        if (reqDto.getMaxParticipants() < 2 || reqDto.getMaxParticipants() > 16) {
            throw new IllegalArgumentException("Max participants should be between 2 and 16");
        }

        List<String> hashtagIds = new ArrayList<>();

        for (String name : reqDto.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(name)
                .orElseGet(() -> Hashtag.builder().name(name).build());

            hashtag.addCount();
            hashtagRepository.save(hashtag);

            hashtagIds.add(hashtag.getId());
        }

        Category category = Category.fromString(reqDto.getCategoryName());

        Meet meet = Meet.builder()
            .title(reqDto.getTitle())
            .description(reqDto.getDescription())
            .category(category)
            .hashtagIds(hashtagIds)
            .hostId(user.getId())
            .maxParticipants(reqDto.getMaxParticipants())
            .isOpen(reqDto.isOpen())
            .build();

        meetRepository.save(meet);

        return meet.getId();
    }
}
