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
import org.umc.spring.service.StoreService.StoreQueryService;
import org.umc.spring.validation.annotation.ExistStore;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/stores")
public class StoreController {

    private final ReviewCommandService reviewService;
    private final StoreQueryService storeQueryService;

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponseDTO.CreateResultDTO> addReview(
            @PathVariable @ExistStore Long storeId,
            @Valid @RequestBody ReviewRequestDTO.CreateDto request,
            @RequestHeader("X-AUTH-ID") Long memberId
    ) {
        Review review = reviewService.addReview(storeId, memberId, request.getContent(), request.getRating());
        return ApiResponse.onSuccess(ReviewConverter.toCreateResultDTO(review));
    }

//    @GetMapping("/{storeId}/reviews")
//    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
//    @ApiResponses({
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//    })
//    @Parameters({
//            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
//    })
//    public ApiResponse<StoreResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistStore @PathVariable(name = "storeId") Long storeId, @RequestParam(name = "page") Integer page){
//        storeQueryService.getReviewList(storeId,page);
//        return null;
//    }
}