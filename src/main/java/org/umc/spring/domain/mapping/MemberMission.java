package org.umc.spring.domain.mapping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.Mission;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.enums.MissionStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'CHALLENGE'")
    private MissionStatus status;

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getMemberMissions().remove(this);
        }
        this.member = member;
    }

    public void setMission(Mission mission) {
        if (this.mission != null) {
            this.mission.getMemberMissions().remove(this);
        }
        this.mission = mission;
    }

    public void updateStatus(MissionStatus status) {
        this.status = status;
    }
}

