package org.umc.spring.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Slice;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.member.response.MemberProfileResponseDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDTO;

public interface MemberQueryService {

  MemberProfileResponseDTO loadMemberProfile(Long memberId);

  Slice<CursorPagedMissionResponseDTO> loadCompletedMissions(Long memberId, Long lastMissionId,
      MissionStatus missionStatus);

  MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request);
}