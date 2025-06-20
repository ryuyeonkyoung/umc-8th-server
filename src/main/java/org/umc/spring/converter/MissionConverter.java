package org.umc.spring.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.umc.spring.domain.Mission;
import org.umc.spring.dto.mission.response.MissionResponseDTO;

public class MissionConverter {

  public static MissionResponseDTO.MissionPreviewDTO toMissionPreviewDTO(Mission mission) {
    return MissionResponseDTO.MissionPreviewDTO.builder()
        .missionId(mission.getId())
        .missionSpec(mission.getMissionSpec())
        .minSpendMoney(mission.getMinSpendMoney())
        .rewardPoints(mission.getRewardPoints())
        .address(mission.getAddress())
        .deadline(mission.getDeadline())
        .build();
  }

  public static MissionResponseDTO.StoreMissionListDTO toStoreMissionListDTO(
      Page<Mission> missionPage) {
    List<MissionResponseDTO.MissionPreviewDTO> missionPreviews = missionPage.stream()
        .map(MissionConverter::toMissionPreviewDTO)
        .collect(Collectors.toList());

    return MissionResponseDTO.StoreMissionListDTO.builder()
        .missionList(missionPreviews)
        .listSize(missionPreviews.size())
        .totalPage(missionPage.getTotalPages())
        .totalElements(missionPage.getTotalElements())
        .isFirst(missionPage.isFirst())
        .isLast(missionPage.isLast())
        .build();
  }
}
