package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.common.BaseEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

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