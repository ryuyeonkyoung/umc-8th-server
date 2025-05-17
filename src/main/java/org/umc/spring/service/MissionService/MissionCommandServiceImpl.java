package org.umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.MemberHandler;
import org.umc.spring.apiPayload.exception.handler.MissionHandler;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.Mission;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.repository.MemberMissionRepository.MemberMissionRepository;
import org.umc.spring.repository.MemberRepository.MemberRepository;
import org.umc.spring.repository.MissionRepository.MissionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandServiceImpl implements MissionCommandService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public MemberMission challengeMission(Long missionId, Long memberId) {
        // 미션은 이미 컨트롤러에서 @ExistMission으로 검증되었으므로 바로 조회
        Mission mission = missionRepository.findById(missionId).get();

        // 회원 존재 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 이미 도전 중인 미션인지 확인
        boolean alreadyChallenged = memberMissionRepository.existsByMissionIdAndMemberId(missionId, memberId);
        if (alreadyChallenged) {
            throw new MissionHandler(ErrorStatus.MISSION_ALREADY_CHALLENGED);
        }

        // 미션 도전 생성
        MemberMission memberMission = MemberMission.builder()
                .mission(mission)
                .member(member)
                .status(MissionStatus.CHALLENGE)
                .build();

        // 양방향 연관관계 설정
        member.addMemberMission(memberMission);

        // 저장 및 반환
        return memberMissionRepository.save(memberMission);
    }
}