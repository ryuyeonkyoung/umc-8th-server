package org.umc.spring.dto.mission.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MissionResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class MissionPreviewDTO {
        private final Long missionId;
        private final String missionSpec;
        private final Integer minSpendMoney;
        private final Integer rewardPoints;
        private final String address;
        private final LocalDate deadline;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class StoreMissionListDTO {
        private final List<MissionPreviewDTO> missionList;
        private final Integer listSize;
        private final Integer totalPage;
        private final Long totalElements;
        private final Boolean isFirst;
        private final Boolean isLast;
    }
}