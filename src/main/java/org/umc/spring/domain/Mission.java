package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.enums.MissionStatus;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String missionSpec;

    private Integer minSpendMoney;

    private Integer rewardPoints;

    private String address;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;
}