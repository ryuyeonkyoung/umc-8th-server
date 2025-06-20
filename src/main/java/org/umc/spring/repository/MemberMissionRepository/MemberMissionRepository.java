package org.umc.spring.repository.MemberMissionRepository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long>,
    MemberMissionRepositoryCustom {

  boolean existsByMissionIdAndMemberId(Long missionId, Long memberId);

  Optional<MemberMission> findByMissionIdAndMemberId(Long missionId, Long memberId);

  Page<MemberMission> findAllByMemberIdAndStatus(Long memberId, MissionStatus status,
      Pageable pageable);
}

