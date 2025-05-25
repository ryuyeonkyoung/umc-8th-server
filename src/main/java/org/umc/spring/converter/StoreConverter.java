package org.umc.spring.converter;

import org.umc.spring.domain.Store;
import org.umc.spring.dto.store.request.StoreRequestDTO;
import org.umc.spring.dto.store.response.StoreResponseDTO;

public class StoreConverter {
    public static StoreResponseDTO.CreateResultDto toCreateResultDTO(Store store) {
        return StoreResponseDTO.CreateResultDto.builder()
                .name(store.getName())
                .address(store.getAddress())
                .score(store.getScore())
                .closedDay(store.getClosedDay())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .build();
    }

    public static Store toStore(StoreRequestDTO.CreateDto request) {
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .score(request.getScore())
                .closedDay(request.getClosedDay())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .build();
    }
}