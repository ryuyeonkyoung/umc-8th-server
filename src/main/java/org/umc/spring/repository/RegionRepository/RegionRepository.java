package org.umc.spring.repository.RegionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
  // Custom query methods can be defined here if needed
}
