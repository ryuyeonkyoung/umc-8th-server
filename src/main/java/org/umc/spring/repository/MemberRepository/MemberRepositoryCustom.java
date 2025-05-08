package org.umc.spring.repository.MemberRepository;

import org.umc.spring.dto.MemberProfileResponseDto;

public interface MemberRepositoryCustom {
    MemberProfileResponseDto findMemberProfileById(Long memberId);
}