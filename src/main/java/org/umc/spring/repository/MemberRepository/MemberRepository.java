package org.umc.spring.repository.MemberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}