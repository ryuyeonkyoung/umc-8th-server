package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.mapping.MemberAgree;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "terms", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MemberAgree> memberAgrees = new HashSet<>();

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String body;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean optional;

    public void addMemberAgree(MemberAgree memberAgree) {
        if (memberAgree == null || this.memberAgrees.contains(memberAgree)) return;
        this.memberAgrees.add(memberAgree);
        memberAgree.setTerms(this);
    }

    /**
     * `memberAgrees` 집합에서 지정된 `MemberAgree`를 제거하고, 해당 `MemberAgree`의 `terms` 참조를 null로 설정합니다.
     *
     * @param memberAgree 제거할 `MemberAgree` 객체. null이거나 집합에 없는 경우 아무 작업도 수행하지 않습니다.
     */
    public void removeMemberAgree(MemberAgree memberAgree) {
        if (memberAgree == null || !this.memberAgrees.contains(memberAgree)) return;
        this.memberAgrees.remove(memberAgree);
        memberAgree.setTerms(null);
    }
}