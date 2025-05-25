package org.umc.spring.service.MissionService;

import org.umc.spring.domain.mapping.MemberMission;

public interface MissionCommandService {
    MemberMission challengeMission(Long missionId, Long memberId);
    MemberMission completeMission(Long missionId, Long memberId);
}

