package org.umc.spring.repository.MissionRepository;

import org.springframework.data.domain.Pageable;
import org.umc.spring.dto.MissionResponseDto;

import java.util.List;

public interface MissionRepositoryCustom {
    List<MissionResponseDto> findAvailableMissionsByRegion(Long memberId, Pageable pageable);
}