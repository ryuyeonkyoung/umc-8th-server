package org.umc.spring.repository.MemberMissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long>, MemberMissionRepositoryCustom {
    boolean existsByMissionIdAndMemberId(Long missionId, Long memberId);
}