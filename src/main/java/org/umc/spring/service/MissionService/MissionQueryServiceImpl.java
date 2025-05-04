package org.umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.umc.spring.dto.MissionResponseDto;
import org.umc.spring.repository.MissionRepository.MissionRepositoryCustom;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepositoryCustom missionRepository;

    @Override
    public Slice<MissionResponseDto> loadHomeMissions(Long memberId) {
        Pageable pageable = PageRequest.of(0, 10);
        List<MissionResponseDto> missions = missionRepository.findAvailableMissionsByRegion(memberId, pageable);

        // Slice로 변환
        boolean hasNext = missions.size() > pageable.getPageSize();
        return new SliceImpl<>(missions, pageable, hasNext);
    }
}