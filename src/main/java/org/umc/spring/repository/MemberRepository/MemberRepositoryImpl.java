package org.umc.spring.repository.MemberRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.QMember;
import org.umc.spring.domain.QMission;
import org.umc.spring.domain.QStore;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.QMemberMission;
import org.umc.spring.dto.mission.response.CursorPagedMissionResponseDto;
import org.umc.spring.dto.member.response.MemberProfileResponseDto;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberProfileResponseDto findMemberProfileById(Long memberId) {
        QMember m = QMember.member;

        // SQL:
        // SELECT nickname, email, phone_number, phone_verified, point
        // FROM member
        // WHERE id = {member_id}
        Member member = queryFactory
                .selectFrom(m)
                .where(m.id.eq(memberId))
                .fetchOne(); // 단일 유저 조회

        if (member == null) {
            throw new IllegalArgumentException("회원 ID " + memberId + "에 해당하는 회원을 찾을 수 없습니다.");
        }

        return MemberProfileResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .phoneVerified(member.getPhoneVerified())
                .point(member.getPoint())
                .build();
    }

    @Override
    public Slice<CursorPagedMissionResponseDto> findCompletedMissionsByCursor(Long memberId, Long lastMissionId, MissionStatus memberMissionStatus) {
        QMission m  = QMission.mission;
        QMemberMission mm = QMemberMission.memberMission;
        QStore s    = QStore.store;

        // 커서 값(lastMissionId)이 없는 경우
        // cursor 첫 페이지 : 커서 없음 → 최신 순서대로 10개 + (11번째가 있으면 "다음 페이지 있음"을 표시하기 위해) 1개(다음 페이지 확인용) 조회
        if (lastMissionId == 0) {
            List<CursorPagedMissionResponseDto > missions = queryFactory
                    .select(Projections.constructor(
                            CursorPagedMissionResponseDto .class,
                            mm.mission.id,               // 1
                            mm.status,                   // 2
                            m.minSpendMoney,             // 3
                            m.rewardPoints,              // 4
                            s.name,                      // 5
                            Expressions.stringTemplate(
                                    "CONCAT(" +
                                            "LPAD({0}, 10, '0')," +
                                            "LPAD({1}, 10, '0')" +
                                            ")",
                                    m.rewardPoints.stringValue(),                                  // ① rewardPoints → String
                                    Expressions.dateTemplate(
                                            Long.class,
                                            "UNIX_TIMESTAMP({0})",
                                            mm.createdAt
                                    ).stringValue()                                                // ② createdAt → UNIX_TIMESTAMP → Long → String
                            )
                    ))
                    .from(mm)
                    .join(mm.mission, m)
                    .join(m.store, s)
                    .where(
                            mm.member.id.eq(memberId),
                            memberMissionStatus != null ? mm.status.eq(memberMissionStatus) : null
                    )
                    .orderBy(m.rewardPoints.desc(), mm.createdAt.desc())
                    .limit(11)
                    .fetch();

            boolean hasNext = missions.size() > 10;
            return new SliceImpl<>(
                    missions.subList(0, Math.min(missions.size(), 10)),
                    PageRequest.of(0, 10),
                    hasNext
            );
        }

        // 커서 값(lastMissionId)이 있는 경우
        var lastCursor = queryFactory
                .select(m.rewardPoints, mm.createdAt)
                .from(mm)
                .join(mm.mission, m)
                .where(mm.id.eq(lastMissionId))
                .fetchOne();

        if (lastCursor == null) {
            throw new IllegalArgumentException("lastMissionId에 해당하는 데이터가 없습니다: " + lastMissionId);
        }

        BooleanBuilder statusCond = new BooleanBuilder();
        if (memberMissionStatus != null) {
            statusCond.and(mm.status.eq(memberMissionStatus));
        }

        List<CursorPagedMissionResponseDto > missions = queryFactory
                .select(Projections.constructor(
                        CursorPagedMissionResponseDto.class,
                        mm.mission.id,               // 1
                        mm.status,                   // 2
                        m.minSpendMoney,             // 3
                        m.rewardPoints,              // 4
                        s.name,                      // 5
                        Expressions.stringTemplate(
                                "CONCAT(" +
                                        "LPAD({0}, 10, '0')," +
                                        "LPAD({1}, 10, '0')" +
                                        ")",
                                m.rewardPoints.stringValue(),                                  // ① rewardPoints → String
                                Expressions.dateTemplate(
                                        Long.class,
                                        "UNIX_TIMESTAMP({0})",
                                        mm.createdAt
                                ).stringValue()                                                // ② createdAt → UNIX_TIMESTAMP → Long → String
                        )
                ))
                .from(mm)
                .join(mm.mission, m)
                .join(m.store, s)
                .where(
                        mm.member.id.eq(memberId),
                        statusCond,
                        new BooleanBuilder()
                                .or(m.rewardPoints.lt(lastCursor.get(0, Integer.class)))
                                .or(m.rewardPoints.eq(lastCursor.get(0, Integer.class))
                                        .and(mm.createdAt.lt(lastCursor.get(1, LocalDateTime.class)))
                                )
                )
                .orderBy(m.rewardPoints.desc(), mm.createdAt.desc())
                .limit(11)
                .fetch();

        boolean hasNext = missions.size() > 10;
        return new SliceImpl<>(
                missions.subList(0, Math.min(missions.size(), 10)),
                PageRequest.of(0, 10),
                hasNext
        );
    }
}