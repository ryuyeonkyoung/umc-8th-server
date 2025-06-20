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
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

  private final ReviewCommandService reviewCommandService;
  private final StoreQueryService storeQueryService;
  private final MissionQueryService missionQueryService;

  @PostMapping(
      value = "/{storeId}/reviews",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE      // JSON + 파일 업로드
  )
  public ApiResponse<ReviewResponseDTO.CreateResultDTO> addReview(
      @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
      @RequestPart("request") @Valid ReviewRequestDTO.CreateDto request,   // 리뷰 본문(JSON)
      @ExistStore @PathVariable Long storeId,                              // 대상 매장
      @RequestHeader("X-AUTH-ID") Long memberId,                           // 작성 회원
      @RequestPart(value = "image", required = false) MultipartFile reviewImage  // 첨부 이미지
  ) {
    Review review = reviewCommandService.createReview(memberId, storeId, request, reviewImage);
    return ApiResponse.onSuccess(ReviewConverter.toCreateResultDTO(review));
  }

  @GetMapping("/{storeId}/reviews")
  @Operation(summary = "특정 가게의 리뷰 목록 조회 API", description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
  })
  @Parameters({
      @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
  })
  public ApiResponse<StoreResponseDTO.ReviewPreViewSliceDTO> getReviewList(
      @ExistStore @PathVariable(name = "storeId") Long storeId,
      @RequestParam(name = "page") Integer page) {
    Slice<Review> reviewSlice = storeQueryService.getReviewList(storeId, page);
    return ApiResponse.onSuccess(StoreConverter.reviewPreViewSliceListDTO(reviewSlice));
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
    if (missionPage.isEmpty()) {
      throw new PageHandler(ErrorStatus.PAGE_NO_DATA);
    }

    return ApiResponse.onSuccess(MissionConverter.toStoreMissionListDTO(missionPage));
  }
}