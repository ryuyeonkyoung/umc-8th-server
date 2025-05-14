package org.umc.spring.converter;

import org.umc.spring.domain.Member;
import org.umc.spring.domain.enums.Gender;
import org.umc.spring.domain.enums.MemberStatus;
import org.umc.spring.domain.enums.SocialType;
import org.umc.spring.dto.MemberRequestDTO;
import org.umc.spring.dto.MemberResponseDTO;

import java.time.LocalDateTime;
import java.util.HashSet;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request) {

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
                .phoneNumber(request.getPhoneNumber())
                .phoneVerified(false)
                .address(request.getAddress())
                .gender(gender)
                .email(request.getEmail())
                .point(request.getPoint())
                .status(status)
                .memberPrefers(new HashSet<>()) // HashSet로 초기화
                .build();
    }
}