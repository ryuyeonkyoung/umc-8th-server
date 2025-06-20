package org.umc.spring.dto.mission.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.umc.spring.domain.enums.MissionStatus;

@Data
@Builder
@Getter
@AllArgsConstructor
public class CursorPagedMissionResponseDTO {

  private Long missionId;           // 1
  private MissionStatus status;     // 2
  private Integer minSpendMoney;    // 3
  private Integer rewardPoints;     // 4
  private String storeName;         // 5
  private String cursorValue;       // 6
}
