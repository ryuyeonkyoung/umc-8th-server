package org.umc.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.converter.StoreConverter;
import org.umc.spring.domain.Store;
import org.umc.spring.dto.store.request.StoreRequestDTO;
import org.umc.spring.dto.store.response.StoreResponseDTO;
import org.umc.spring.service.StoreService.StoreCommandService;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {

    private final StoreCommandService storeService;

    @PostMapping("/{regionId}/stores")
    public ApiResponse<StoreResponseDTO.CreateResultDto> createStore(@PathVariable Long regionId, @RequestBody @Valid StoreRequestDTO.CreateDto request) {
        Store store = storeService.addStore(request, regionId);
        return ApiResponse.onSuccess(StoreConverter.toCreateResultDTO(store));
    }
}