package org.umc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.umc.spring.domain.Uuid;

@Repository
public interface UuidRepository extends JpaRepository<Uuid, Long> {

}
