package org.umc.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.converter.MemberMissionConverter;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.dto.membermission.response.MemberMissionResponseDTO;
import org.umc.spring.service.MissionService.MissionCommandService;
import org.umc.spring.validation.annotation.ExistMission;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
@Validated // 메소드 파라미터 검증을 위한 어노테이션
public class MissionController {

    private final MissionCommandService missionCommandService;

    @PostMapping("/{missionId}/member-missions")
    public ApiResponse<MemberMissionResponseDTO.CreateResultDTO> challengeMission(
            @PathVariable @ExistMission Long missionId,
            @RequestHeader("X-AUTH-ID") Long memberId
    ) {
        MemberMission memberMission = missionCommandService.challengeMission(missionId, memberId);
        return ApiResponse.onSuccess(MemberMissionConverter.toCreateResultDTO(memberMission));
    }
}