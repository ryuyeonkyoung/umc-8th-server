package org.umc.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.PageHandler;
import org.umc.spring.converter.MemberConverter;
import org.umc.spring.converter.ReviewConverter;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.Review;
import org.umc.spring.dto.member.request.MemberRequestDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;
import org.umc.spring.dto.review.response.ReviewResponseDTO;
import org.umc.spring.service.MemberService.MemberCommandService;
import org.umc.spring.service.ReviewService.ReviewQueryService;
import org.umc.spring.validation.annotation.CheckPage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Validated
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request) {
        Member member = memberCommandService.joinMember(request); // 회원가입 처리
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member)); // 생성된 memberId와 createdAt을 반환
    }

    @GetMapping("/my-reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "내가 작성한 리뷰 목록을 조회하는 API이며, 페이징을 포함합니다. query String으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4001", description = "페이지 번호는 1 이상이어야 합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ReviewResponseDTO.MyReviewListDTO> getMyReviews(
            @RequestHeader("X-AUTH-ID") Long memberId,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam @CheckPage Integer page
    ) {
        Slice<Review> reviewSlice = reviewQueryService.getMyReviews(memberId, page - 1);

        // 페이지가 비어있을 경우 예외 처리
        if(reviewSlice.isEmpty()) {
            throw new PageHandler(ErrorStatus.PAGE_NO_DATA);
        }

        return ApiResponse.onSuccess(ReviewConverter.toMyReviewListDTO(reviewSlice));
    }
}

