package org.umc.spring.repository.MissionRepository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.umc.spring.dto.mission.response.MissionResponseDTO;

public interface MissionRepositoryCustom {

  List<MissionResponseDTO> findAvailableMissionsByRegion(Long memberId, Pageable pageable);
}

