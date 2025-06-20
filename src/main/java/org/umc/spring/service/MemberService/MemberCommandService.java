package org.umc.spring.service.MemberService;

import org.umc.spring.domain.Member;
import org.umc.spring.dto.member.request.MemberRequestDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;

public interface MemberCommandService {

  Member joinMember(MemberRequestDTO.JoinDto request);

  MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request);
}
