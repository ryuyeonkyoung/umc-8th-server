package org.umc.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.PageHandler;
import org.umc.spring.converter.MemberMissionConverter;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.dto.membermission.response.MemberMissionResponseDTO;
import org.umc.spring.service.MissionService.MissionCommandService;
import org.umc.spring.service.MissionService.MissionQueryService;
import org.umc.spring.validation.annotation.CheckPage;
import org.umc.spring.validation.annotation.ExistMission;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
@Validated // 메소드 파라미터 검증을 위한 어노테이션
public class MissionController {

    private final MissionCommandService missionCommandService;
    private final MissionQueryService missionQueryService;

    @PostMapping("/{missionId}/member-missions")
    @Operation(summary = "미션 도전 API", description = "특정 미션에 도전하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4001", description = "미션을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4002", description = "이미 도전 중인 미션입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<MemberMissionResponseDTO.CreateResultDTO> challengeMission(
            @PathVariable @ExistMission Long missionId,
            @RequestHeader("X-AUTH-ID") Long memberId
    ) {
        MemberMission memberMission = missionCommandService.challengeMission(missionId, memberId);
        return ApiResponse.onSuccess(MemberMissionConverter.toCreateResultDTO(memberMission));
    }

    @PatchMapping("/{missionId}/complete")
    @Operation(summary = "미션 완료 API", description = "진행 중인 미션을 완료 상태로 변경하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4001", description = "미션을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4003", description = "이미 완료된 미션입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4004", description = "해당 회원의 미션을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<MemberMissionResponseDTO.CompleteResultDTO> completeMission(
            @Parameter(description = "완료할 미션의 ID")
            @PathVariable @ExistMission Long missionId,
            @RequestHeader("X-AUTH-ID") Long memberId
    ) {
        MemberMission memberMission = missionCommandService.completeMission(missionId, memberId);
        return ApiResponse.onSuccess(MemberMissionConverter.toCompleteResultDTO(memberMission));
    }

    @GetMapping("/my-missions")
    @Operation(summary = "내 미션 목록 조회 API", description = "내가 진행중인 미션 목록을 조회하는 API이며, 페이징을 포함합니다. query String으로 page 번호를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4001", description = "페이지 번호는 1 이상이어야 합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PAGE4002", description = "해당 페이지에 데이터가 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<MemberMissionResponseDTO.MemberMissionListDTO> getMyMissions(
            @RequestHeader("X-AUTH-ID") Long memberId,
            @Parameter(description = "페이지 번호(1부터 시작)")
            @RequestParam @CheckPage Integer page
    ) {
        // 1부터 시작하는 페이지 번호를 0부터 시작하는 인덱스로 변환
        Page<MemberMission> missionPage = missionQueryService.getMyMissions(memberId, MissionStatus.CHALLENGE, page - 1);

        // 페이지가 비어있을 경우 예외 처리
        if(missionPage.isEmpty()) {
            throw new PageHandler(ErrorStatus.PAGE_NO_DATA);
        }

        return ApiResponse.onSuccess(MemberMissionConverter.toMemberMissionListDTO(missionPage));
    }
}

