package org.umc.spring.dto.membermission.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.umc.spring.domain.enums.MissionStatus;

import java.time.LocalDateTime;

public class MemberMissionResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO {
        private Long id;
        private Long missionId;
        private Long memberId;
        private MissionStatus status;
        private LocalDateTime createdAt;
    }
}