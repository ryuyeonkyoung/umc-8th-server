package org.umc.spring.dto.mission.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class MissionResponseDto {
    private final Long missionId;
    private final Integer minSpendMoney;
    private final Integer rewardPoints;
    private final String storeName;
    private final String storeAddress;

}