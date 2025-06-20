package org.umc.spring.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.dto.membermission.response.MemberMissionResponseDTO;

public class MemberMissionConverter {

  public static MemberMissionResponseDTO.CreateResultDTO toCreateResultDTO(
      MemberMission memberMission) {
    return MemberMissionResponseDTO.CreateResultDTO.builder()
        .id(memberMission.getId())
        .missionId(memberMission.getMission().getId())
        .memberId(memberMission.getMember().getId())
        .status(memberMission.getStatus())
        .createdAt(memberMission.getCreatedAt())
        .build();
  }

  public static MemberMissionResponseDTO.CompleteResultDTO toCompleteResultDTO(
      MemberMission memberMission) {
    return MemberMissionResponseDTO.CompleteResultDTO.builder()
        .id(memberMission.getId())
        .missionId(memberMission.getMission().getId())
        .memberId(memberMission.getMember().getId())
        .status(memberMission.getStatus())
        .updatedAt(memberMission.getUpdatedAt())
        .rewardPoints(memberMission.getMission().getRewardPoints())
        .build();
  }

  public static MemberMissionResponseDTO.MemberMissionPreviewDTO toMemberMissionPreviewDTO(
      MemberMission memberMission) {
    return MemberMissionResponseDTO.MemberMissionPreviewDTO.builder()
        .id(memberMission.getId())
        .missionId(memberMission.getMission().getId())
        .missionSpec(memberMission.getMission().getMissionSpec())
        .status(memberMission.getStatus())
        .rewardPoints(memberMission.getMission().getRewardPoints())
        .storeName(memberMission.getMission().getStore().getName())
        .challengedAt(memberMission.getCreatedAt())
        .completedAt(memberMission.getUpdatedAt())
        .build();
  }

  public static MemberMissionResponseDTO.MemberMissionListDTO toMemberMissionListDTO(
      Page<MemberMission> missionPage) {
    List<MemberMissionResponseDTO.MemberMissionPreviewDTO> missionPreviewList = missionPage.stream()
        .map(MemberMissionConverter::toMemberMissionPreviewDTO)
        .collect(Collectors.toList());

    return MemberMissionResponseDTO.MemberMissionListDTO.builder()
        .missionList(missionPreviewList)
        .listSize(missionPreviewList.size())
        .totalPage(missionPage.getTotalPages())
        .totalElements(missionPage.getTotalElements())
        .isFirst(missionPage.isFirst())
        .isLast(missionPage.isLast())
        .build();
  }
}

