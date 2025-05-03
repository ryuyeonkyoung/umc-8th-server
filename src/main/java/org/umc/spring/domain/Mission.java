package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.common.BaseEntity;
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

    @Builder.Default
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<MemberMission> memberMissions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Lob
    @Column(nullable = false)
    private String missionSpec;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1000")
    private Integer minSpendMoney;

    @Version
    @Column(nullable = false)
    private Integer rewardPoints;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false)
    private LocalDate deadline;

}