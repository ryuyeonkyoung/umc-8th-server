package org.umc.spring.service.MissionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.umc.spring.domain.Mission;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.dto.mission.response.MissionResponseDTO;

public interface MissionQueryService {
    Slice<MissionResponseDTO> loadHomeMissions(Long memberId);
    boolean existsById(Long id);
    Page<Mission> getStoreAllMissions(Long storeId, Integer page);
    Page<MemberMission> getMyMissions(Long memberId, MissionStatus status, Integer page);
}

