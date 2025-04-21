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

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    private Float score;

    // 주 1회 정기휴무
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek closedDay;

    // 여러 요일 휴무
    // @ElementCollection
    // private List<DayOfWeek> closedDays = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TIME DEFAULT '08:00:00'")
    private LocalTime openTime;

    @Column(nullable = false, columnDefinition = "TIME DEFAULT '23:59:00'")
    private LocalTime closeTime;
}