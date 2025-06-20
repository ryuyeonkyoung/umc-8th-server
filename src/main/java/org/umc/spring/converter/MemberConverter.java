package org.umc.spring.converter;

import java.time.LocalDateTime;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.enums.Gender;
import org.umc.spring.domain.enums.MemberStatus;
import org.umc.spring.domain.enums.SocialType;
import org.umc.spring.dto.member.request.MemberRequestDTO;
import org.umc.spring.dto.member.response.MemberResponseDTO;

@Slf4j
public class MemberConverter {

  public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
    return MemberResponseDTO.JoinResultDTO.builder()
        .memberId(member.getId())
        .createdAt(LocalDateTime.now())
        .build();
  }

  public static MemberResponseDTO.LoginResultDTO toLoginResultDTO(Long memberId,
      String accessToken) {
    return MemberResponseDTO.LoginResultDTO.builder()
        .memberId(memberId)
        .accessToken(accessToken)
        .build();
  }

  public static MemberResponseDTO.MemberInfoDTO toMemberInfoDTO(Member member) {
    return MemberResponseDTO.MemberInfoDTO.builder()
        .email(member.getEmail())
        .name(member.getName())
        .gender(member.getGender().toString())
        .build();
  }

  public static Member toMember(MemberRequestDTO.JoinDto request) {

    log.info("request: {}", request.toString());

    SocialType socialType = switch (request.getSocialType()) {
      case 1 -> SocialType.KAKAO;
      case 2 -> SocialType.NAVER;
      case 3 -> SocialType.GOOGLE;
      default -> null;
    };

    Gender gender = switch (request.getGender()) {
      case 1 -> Gender.MALE;
      case 2 -> Gender.FEMALE;
      case 3 -> Gender.NONE;
      default -> null;
    };

    MemberStatus status = switch (request.getStatus()) {
      case 1 -> MemberStatus.ACTIVE;
      case 2 -> MemberStatus.INACTIVE;
      case 3 -> MemberStatus.SUSPENDED;
      default -> null;
    };

    return Member.builder()
        .name(request.getName())
        .nickname(request.getNickname())
        .socialId(request.getSocialId())
        .socialType(socialType)
//                .socialType(SocialType.KAKAO)
        .phoneNumber(request.getPhoneNumber())
        .phoneVerified(false)
        .address(request.getAddress())
        .gender(gender)
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .point(0) // 기본 포인트 0으로 설정
        .status(status)
        .memberPrefers(new HashSet<>()) // HashSet로 초기화
        .build();
  }
}