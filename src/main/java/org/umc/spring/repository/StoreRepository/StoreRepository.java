package org.umc.spring.repository.StoreRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

}
