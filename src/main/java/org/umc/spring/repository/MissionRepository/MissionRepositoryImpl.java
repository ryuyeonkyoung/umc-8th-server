package org.umc.spring.repository.MissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.umc.spring.domain.QMission;
import org.umc.spring.domain.QRegion;
import org.umc.spring.domain.QStore;
import org.umc.spring.domain.mapping.QMemberMission;
import org.umc.spring.dto.mission.response.MissionResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MissionResponseDTO> findAvailableMissionsByRegion(Long regionId, Pageable pageable) {
        QMission mission = QMission.mission;
        QMemberMission memberMission = new QMemberMission("memberMission");
        QStore store = QStore.store;
        QRegion region = QRegion.region;

        BooleanBuilder builder = new BooleanBuilder();

        // 지역 ID 조건
        if (regionId != null) {
            builder.and(store.region.id.eq(regionId));
        }

        // 마감되지 않은 미션 조건
        LocalDateTime now = LocalDateTime.now();
        builder.and(mission.deadline.gt(LocalDate.now()));

        return queryFactory
                .select(Projections.constructor(MissionResponseDTO.class,
                        mission.id,
                        mission.minSpendMoney,
                        mission.rewardPoints,
                        store.name,
                        store.address
                ))
                .from(mission)
                .join(mission.store, store)
                .join(store.region, region) // region 명시적 조인
                .join(memberMission).on(memberMission.mission.id.eq(mission.id))
                .where(builder)
                .orderBy(mission.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}