package org.umc.spring.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.QMember;
import org.umc.spring.dto.MemberProfileResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;


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
}