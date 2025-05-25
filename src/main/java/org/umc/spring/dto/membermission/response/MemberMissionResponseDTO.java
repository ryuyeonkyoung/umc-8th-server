package org.umc.spring.dto.membermission.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.umc.spring.domain.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.List;

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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompleteResultDTO {
        private Long id;
        private Long missionId;
        private Long memberId;
        private MissionStatus status;
        private LocalDateTime updatedAt;
        private Integer rewardPoints;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberMissionPreviewDTO {
        private Long id;
        private Long missionId;
        private String missionSpec;
        private MissionStatus status;
        private Integer rewardPoints;
        private String storeName;
        private LocalDateTime challengedAt;
        private LocalDateTime completedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberMissionListDTO {
        private List<MemberMissionPreviewDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}

