package org.umc.spring.repository.MissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Mission;
import org.umc.spring.domain.Store;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {

  Page<Mission> findAllByStore(Store store, PageRequest pageRequest);
}

