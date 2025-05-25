package org.umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDTO;
import org.umc.spring.dto.member.response.MemberProfileResponseDTO;
import org.umc.spring.repository.MemberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public MemberProfileResponseDTO loadMemberProfile(Long memberId) {
        return memberRepository.findMemberProfileById(memberId);
    }

    @Override
    public Slice<CursorPagedMissionResponseDTO> loadCompletedMissions(Long memberId, Long lastMissionId, MissionStatus missionStatus) {
        return memberRepository.findCompletedMissionsByCursor(memberId, lastMissionId, missionStatus);
    }
}