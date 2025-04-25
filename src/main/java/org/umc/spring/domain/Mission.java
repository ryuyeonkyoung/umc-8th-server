package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.enums.MissionStatus;
import org.umc.spring.domain.mapping.MemberMission;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<MemberMission> memberMissions = new HashSet<>();

    @Lob
    @Column(nullable = false)
    private String missionSpec;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1000")
    private Integer minSpendMoney;

    @Column(nullable = false)
    private Integer rewardPoints;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'CHALLENGING'")
    private MissionStatus status;
}