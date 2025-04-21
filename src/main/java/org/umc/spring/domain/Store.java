package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Float score;

    // 주 1회 정기휴무
    @Enumerated(EnumType.STRING)
    private DayOfWeek closedDay;

    // 여러 요일 휴무
    // @ElementCollection
    // private List<DayOfWeek> closedDays = new ArrayList<>();

    private LocalTime openTime;

    private LocalTime closeTime;
}