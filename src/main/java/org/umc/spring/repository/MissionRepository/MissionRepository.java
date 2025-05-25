package org.umc.spring.repository.MissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
}