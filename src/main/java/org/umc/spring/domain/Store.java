package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicInsert
@DynamicUpdate
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
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

    /**
     * 매장의 지역 정보를 변경합니다.
     *
     * 이전에 설정된 지역이 있다면 해당 지역의 매장 목록에서 이 매장을 제거한 후, 새로운 지역으로 변경합니다.
     *
     * @param region 새로 지정할 지역
     */
    public void setRegion(Region region) {
        if (this.region != null) {
            this.region.getStores().remove(this);
        }
        this.region = region;
    }

    /**
     * 이 매장의 주요 정보를 문자열로 반환합니다.
     *
     * @return 매장의 id, 이름, 주소, 평점, 지역명을 포함한 문자열
     */
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