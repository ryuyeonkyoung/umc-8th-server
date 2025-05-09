package org.umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.CursorPagedMissionResponseDto;
import org.umc.spring.dto.MemberProfileResponseDto;
import org.umc.spring.repository.MemberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public MemberProfileResponseDto loadMemberProfile(Long memberId) {
        return memberRepository.findMemberProfileById(memberId);
    }

    @Override
    public Slice<CursorPagedMissionResponseDto> loadCompletedMissions(Long memberId, Long lastMissionId, MissionStatus missionStatus) {
        return memberRepository.findCompletedMissionsByCursor(memberId, lastMissionId, missionStatus);
    }
}