package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.enums.CommentStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private CommentStatus status;

    /**
     * 이 댓글의 연관된 리뷰를 변경합니다.
     *
     * 기존에 연결된 리뷰가 있다면 해당 리뷰의 댓글 목록에서 이 댓글을 제거하고,
     * 새로운 리뷰로 연관을 설정합니다.
     *
     * @param review 새로 연관할 리뷰 엔티티
     */
    public void setReview(Review review) {
        if (this.review != null) {
            this.review.getComments().remove(this);
        }
        this.review = review;
    }
}