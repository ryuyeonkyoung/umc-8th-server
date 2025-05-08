package org.umc.spring.service.MemberService;

import org.umc.spring.dto.MemberProfileResponseDto;

public interface MemberQueryService {
    MemberProfileResponseDto loadMemberProfile(Long memberId);
}