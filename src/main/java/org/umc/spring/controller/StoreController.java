package org.umc.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.PageHandler;
import org.umc.spring.converter.MissionConverter;
import org.umc.spring.converter.ReviewConverter;
import org.umc.spring.converter.StoreConverter;
import org.umc.spring.domain.Mission;
import org.umc.spring.domain.Review;
import org.umc.spring.dto.mission.response.MissionResponseDTO;
import org.umc.spring.dto.review.request.ReviewRequestDTO;
import org.umc.spring.dto.review.response.ReviewResponseDTO;
import org.umc.spring.dto.store.response.StoreResponseDTO;
import org.umc.spring.service.MissionService.MissionQueryService;
import org.umc.spring.service.ReviewService.ReviewCommandService;
import org.umc.spring.service.StoreService.StoreQueryService;
import org.umc.spring.validation.annotation.CheckPage;
import org.umc.spring.validation.annotation.ExistStore;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/stores")
public class StoreController {

    private final ReviewCommandService reviewService;
    private final StoreQueryService storeQueryService;
    private final MissionQueryService missionQueryService;

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponseDTO.CreateResultDTO> addReview(
            @PathVariable @ExistStore Long storeId,
            @Valid @RequestBody ReviewRequestDTO.CreateDto request,
            @RequestHeader("X-AUTH-ID") Long memberId
    ) {
        Review review = reviewService.addReview(storeId, memberId, request.getContent(), request.getRating());
        return ApiResponse.onSuccess(ReviewConverter.toCreateResultDTO(review));
    }

    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistStore @PathVariable(name = "storeId") Long storeId,@RequestParam(name = "page") Integer page){
        Page<Review> reviewList = storeQueryService.getReviewList(storeId, page);
        return ApiResponse.onSuccess(StoreConverter.reviewPreViewListDTO(reviewList));
    }

    @GetMapping("/{storeId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조회 API", description = "특정 가게의 미션 목록을 조회하는 API이며, 페이징을 포함합니다. query String으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4005", description = "가게가 없습니다", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4001", description = "페이지 번호는 1 이상이어야 합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4002", description = "해당 페이지에 데이터가 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<MissionResponseDTO.StoreMissionListDTO> getStoreMissions(
            @Parameter(description = "조회할 가게의 ID")
            @PathVariable @ExistStore Long storeId,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam @CheckPage Integer page
    ) {
        // 1부터 시작하는 페이지 번호를 0부터 시작하는 인덱스로 변환
        Page<Mission> missionPage = missionQueryService.getStoreAllMissions(storeId, page - 1);

        // 페이지가 비어있을 경우 예외 처리
        if(missionPage.isEmpty()) {
            throw new PageHandler(ErrorStatus.PAGE_NO_DATA);
        }

        return ApiResponse.onSuccess(MissionConverter.toStoreMissionListDTO(missionPage));
    }
}