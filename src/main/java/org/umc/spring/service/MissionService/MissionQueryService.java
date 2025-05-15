package org.umc.spring.service.MissionService;

import org.springframework.data.domain.Slice;
import org.umc.spring.dto.mission.response.MissionResponseDto;

public interface MissionQueryService {
    Slice<MissionResponseDto> loadHomeMissions(Long memberId);
}