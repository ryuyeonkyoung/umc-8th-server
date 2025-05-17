package org.umc.spring.converter;

import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.dto.membermission.response.MemberMissionResponseDTO;

public class MemberMissionConverter {

    public static MemberMissionResponseDTO.CreateResultDTO toCreateResultDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.CreateResultDTO.builder()
                .id(memberMission.getId())
                .missionId(memberMission.getMission().getId())
                .memberId(memberMission.getMember().getId())
                .status(memberMission.getStatus())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }
}