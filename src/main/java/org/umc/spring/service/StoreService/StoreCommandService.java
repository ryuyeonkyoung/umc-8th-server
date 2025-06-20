package org.umc.spring.service.StoreService;

import org.umc.spring.domain.Store;
import org.umc.spring.dto.store.request.StoreRequestDTO;

public interface StoreCommandService {

  Store addStore(StoreRequestDTO.CreateDto request, Long regionId);
}
