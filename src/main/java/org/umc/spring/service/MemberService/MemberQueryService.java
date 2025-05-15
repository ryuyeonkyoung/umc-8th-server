package org.umc.spring.service.MemberService;

import org.springframework.data.domain.Slice;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDto;
import org.umc.spring.dto.member.response.MemberProfileResponseDto;

public interface MemberQueryService {
    MemberProfileResponseDto loadMemberProfile(Long memberId);
    Slice<CursorPagedMissionResponseDto> loadCompletedMissions(Long memberId, Long lastMissionId, MissionStatus missionStatus);
}