package org.umc.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.converter.MemberConverter;
import org.umc.spring.domain.Member;
import org.umc.spring.dto.member.request.MemberRequestDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;
import org.umc.spring.service.MemberService.MemberCommandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request) {
        Member member = memberCommandService.joinMember(request); // 회원가입 처리
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member)); // 생성된 memberId와 createdAt을 반환
    }
}