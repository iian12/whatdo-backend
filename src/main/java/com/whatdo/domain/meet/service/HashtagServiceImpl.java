package com.whatdo.domain.meet.service;

import com.whatdo.domain.meet.dto.HashtagDto;
import com.whatdo.domain.meet.repository.HashtagRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    public HashtagServiceImpl(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    public List<HashtagDto> getHashtagsById(List<String> hashtagIds) {
        return hashtagRepository.findAllById(hashtagIds).stream()
            .map(hashtag -> new HashtagDto(hashtag.getId(), hashtag.getName()))
            .collect(Collectors.toList());
    }
}
