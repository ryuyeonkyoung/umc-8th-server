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

    @Version
    @Builder.Default
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long version = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
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

    public void addReview(Review review) {
        if (review == null || this.reviews.contains(review)) return;
        this.reviews.add(review);
        review.setStore(this);
    }

    public void removeReview(Review review) {
        if (review == null || !this.reviews.contains(review)) return;
        this.reviews.remove(review);
        review.setStore(null);
    }

    public void setRegion(Region region) {
        if (this.region != null) {
            this.region.getStores().remove(this);
        }
        this.region = region;
    }

    @PrePersist
    private void prePersist() {
        if (this.openTime == null) {
            this.openTime = LocalTime.of(8, 0);
        }
        if (this.closeTime == null) {
            this.closeTime = LocalTime.of(23, 59);
        }
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", score=" + score +
                ", region=" + (region != null ? region.getName() : "N/A") + // region의 이름 출력
                '}';
    }
}