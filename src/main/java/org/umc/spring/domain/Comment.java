package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.enums.CommentStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void setReview(Review review) {
        if (this.review != null) {
            this.review.getComments().remove(this);
        }
        this.review = review;
    }

    @PrePersist
    private void prePersist() {
        if (this.status == null) {
            this.status = CommentStatus.ACTIVE;
        }
    }
}