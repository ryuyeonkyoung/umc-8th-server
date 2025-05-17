package org.umc.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.converter.ReviewConverter;
import org.umc.spring.domain.Review;
import org.umc.spring.dto.review.request.ReviewRequestDTO;
import org.umc.spring.dto.review.response.ReviewResponseDTO;
import org.umc.spring.service.ReviewService.ReviewCommandService;
import org.umc.spring.validation.annotation.ExistStore;

@RestController
@RequestMapping("/stores/{storeId}/reviews")
@RequiredArgsConstructor
@Validated
public class StoreReviewController {

    private final ReviewCommandService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponseDTO.CreateResultDTO> addReview(
            @PathVariable @ExistStore Long storeId,
            @Valid @RequestBody ReviewRequestDTO.CreateDto request,
            @RequestHeader("X-AUTH-ID") Long memberId
    ) {
        Review review = reviewService.addReview(storeId, memberId, request.getContent(), request.getRating());
        return ApiResponse.onSuccess(ReviewConverter.toCreateResultDTO(review));
    }
}