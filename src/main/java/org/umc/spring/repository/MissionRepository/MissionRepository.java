package org.umc.spring.repository.MissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Member;

public interface MissionRepository extends JpaRepository<Member, Long>, MissionRepositoryCustom {
}