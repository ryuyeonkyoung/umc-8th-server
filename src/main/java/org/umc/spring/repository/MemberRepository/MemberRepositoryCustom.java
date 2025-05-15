package org.umc.spring.repository.MemberRepository;

import org.springframework.data.domain.Slice;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDto;
import org.umc.spring.dto.member.response.MemberProfileResponseDto;

public interface MemberRepositoryCustom {
    MemberProfileResponseDto findMemberProfileById(Long memberId);
    Slice<CursorPagedMissionResponseDto> findCompletedMissionsByCursor(Long memberId, Long lastMissionId, MissionStatus memberMissionStatus);

}