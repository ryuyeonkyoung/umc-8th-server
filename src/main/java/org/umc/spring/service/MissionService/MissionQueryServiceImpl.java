package org.umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.StoreHandler;
import org.umc.spring.domain.Mission;
import org.umc.spring.domain.Store;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.dto.mission.response.MissionResponseDTO;
import org.umc.spring.repository.MemberMissionRepository.MemberMissionRepository;
import org.umc.spring.repository.MissionRepository.MissionRepository;
import org.umc.spring.service.StoreService.StoreQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;
    private final StoreQueryService storeQueryService;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public Slice<MissionResponseDTO> loadHomeMissions(Long memberId) {
        Pageable pageable = PageRequest.of(0, 10);
        List<MissionResponseDTO> missions = missionRepository.findAvailableMissionsByRegion(memberId, pageable);

        // Slice로 변환
        boolean hasNext = missions.size() > pageable.getPageSize();
        return new SliceImpl<>(missions, pageable, hasNext);
    }

    @Override
    public boolean existsById(Long id) {
        return missionRepository.existsById(id);
    }

    @Override
    public Page<Mission> getStoreAllMissions(Long storeId, Integer page) {
        // 가게 존재 여부 확인
        Store store = storeQueryService.findStore(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));

        // 페이징 처리 (10개씩)
        PageRequest pageRequest = PageRequest.of(page, 10);

        // 가게별 미션 목록 조회
        return missionRepository.findAllByStore(store, pageRequest);
    }

    @Override
    public Page<MemberMission> getMyMissions(Long memberId, MissionStatus status, Integer page) {
        // 페이징 처리 (10개씩)
        PageRequest pageRequest = PageRequest.of(page, 10);

        // 회원별, 상태별 미션 목록 조회
        return memberMissionRepository.findAllByMemberIdAndStatus(memberId, status, pageRequest);
    }
}
