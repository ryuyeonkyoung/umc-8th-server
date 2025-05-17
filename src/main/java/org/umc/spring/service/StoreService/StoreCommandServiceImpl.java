package org.umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.RegionHandler;
import org.umc.spring.converter.StoreConverter;
import org.umc.spring.domain.Region;
import org.umc.spring.domain.Store;
import org.umc.spring.dto.store.request.StoreRequestDTO;
import org.umc.spring.repository.RegionRepository.RegionRepository;
import org.umc.spring.repository.StoreRepository.StoreRepository;


@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final RegionRepository regionRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Store addStore(StoreRequestDTO.CreateDto request, Long regionId) {

        Store store = StoreConverter.toStore(request);

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RegionHandler(ErrorStatus.REGION_NOT_FOUND));
        store.setRegion(region);

        return storeRepository.save(store);
    }
}