package org.umc.spring.repository.MemberRepository;

import org.springframework.data.domain.Slice;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.member.response.MemberProfileResponseDTO;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDTO;

public interface MemberRepositoryCustom {

  MemberProfileResponseDTO findMemberProfileById(Long memberId);

  Slice<CursorPagedMissionResponseDTO> findCompletedMissionsByCursor(Long memberId,
      Long lastMissionId, MissionStatus memberMissionStatus);

}