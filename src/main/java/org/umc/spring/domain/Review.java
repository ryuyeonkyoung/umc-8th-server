package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.enums.ReviewStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<ReviewImage> reviewImageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Comment> commentList = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Lob
    @Column(nullable = false)
    private String context;

    @Column(nullable = false)
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private ReviewStatus status;

    @Version
    @Builder.Default
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long version = 0L;

    public void addReviewImage(ReviewImage reviewImage) {
        if (reviewImage == null || this.reviewImageList.contains(reviewImage)) return;
        this.reviewImageList.add(reviewImage);
        reviewImage.setReview(this);
    }

    public void removeReviewImage(ReviewImage reviewImage) {
        if (reviewImage == null || !this.reviewImageList.contains(reviewImage)) return;
        this.reviewImageList.remove(reviewImage);
        reviewImage.setReview(null);
    }

    public void addComment(Comment comment) {
        if (comment == null || this.commentList.contains(comment)) return;
        this.commentList.add(comment);
        comment.setReview(this);
    }

    public void removeComment(Comment comment) {
        if (comment == null || !this.commentList.contains(comment)) return;
        this.commentList.remove(comment);
        comment.setReview(null);
    }

    public void setStore(Store store) {
        if (this.store != null) {
            this.store.getReviews().remove(this);
        }
        this.store = store;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getReviews().remove(this);
        }
        this.member = member;
    }

    @PrePersist
    private void prePersist() {
        if (this.status == null) {
            this.status = ReviewStatus.ACTIVE;
        }
    }
}