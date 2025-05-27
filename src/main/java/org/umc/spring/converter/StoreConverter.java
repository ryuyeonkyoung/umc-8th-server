package org.umc.spring.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.umc.spring.domain.Review;
import org.umc.spring.domain.Store;
import org.umc.spring.dto.store.request.StoreRequestDTO;
import org.umc.spring.dto.store.response.StoreResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public static StoreResponseDTO.ReviewPreViewDTO reviewPreViewDTO(Review review){
        return StoreResponseDTO.ReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName()) // 객체 그래프 탐색
                .score(review.getRating())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getContext())
                .build();
    }

    public static StoreResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO(Page<Review> reviewList){

        List<StoreResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(StoreConverter::reviewPreViewDTO).collect(Collectors.toList());

        return StoreResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }

    public static StoreResponseDTO.ReviewPreViewSliceDTO reviewPreViewSliceListDTO(Slice<Review> reviewSlice){

        List<StoreResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewSlice.stream()
                .map(StoreConverter::reviewPreViewDTO).collect(Collectors.toList());

        return StoreResponseDTO.ReviewPreViewSliceDTO.builder()
                .reviewList(reviewPreViewDTOList)
                .hasNext(reviewSlice.hasNext())
                .build();
    }
}