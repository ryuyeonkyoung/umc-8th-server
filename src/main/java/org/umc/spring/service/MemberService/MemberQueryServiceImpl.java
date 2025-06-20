package org.umc.spring.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.MemberHandler;
import org.umc.spring.config.security.jwt.JwtTokenProvider;
import org.umc.spring.converter.MemberConverter;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.dto.member.response.MemberProfileResponseDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDTO;
import org.umc.spring.repository.MemberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public MemberProfileResponseDTO loadMemberProfile(Long memberId) {
    return memberRepository.findMemberProfileById(memberId);
  }

  @Override
  public Slice<CursorPagedMissionResponseDTO> loadCompletedMissions(Long memberId,
      Long lastMissionId, MissionStatus missionStatus) {
    return memberRepository.findCompletedMissionsByCursor(memberId, lastMissionId, missionStatus);
  }

  @Override
  @Transactional(readOnly = true)
  public MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request) {
    Authentication authentication = jwtTokenProvider.extractAuthentication(request);
    String email = authentication.getName();

    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    return MemberConverter.toMemberInfoDTO(member);
  }
}