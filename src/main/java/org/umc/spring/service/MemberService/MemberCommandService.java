package org.umc.spring.service.MemberService;

import org.umc.spring.domain.Member;
import org.umc.spring.dto.MemberRequestDTO;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinDto request);
}
