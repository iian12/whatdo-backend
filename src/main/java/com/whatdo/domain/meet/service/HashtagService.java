package com.whatdo.domain.meet.service;

import com.whatdo.domain.meet.dto.HashtagDto;
import java.util.List;

public interface HashtagService {

    public List<HashtagDto> getHashtagsById(List<String> hashtagIds);
}
