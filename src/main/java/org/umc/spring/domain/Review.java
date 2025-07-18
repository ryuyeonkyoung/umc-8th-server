package org.umc.spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.enums.ReviewStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ReviewImage> reviewImages = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Comment> comments = new HashSet<>();

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
  @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
  private Long version = 0L;

  public void addReviewImage(ReviewImage reviewImage) {
      if (reviewImage == null || this.reviewImages.contains(reviewImage)) {
          return;
      }
    this.reviewImages.add(reviewImage);
    reviewImage.setReview(this);
  }

  public void removeReviewImage(ReviewImage reviewImage) {
      if (reviewImage == null || !this.reviewImages.contains(reviewImage)) {
          return;
      }
    this.reviewImages.remove(reviewImage);
    reviewImage.setReview(null);
  }

  public void addComment(Comment comment) {
      if (comment == null || this.comments.contains(comment)) {
          return;
      }
    this.comments.add(comment);
    comment.setReview(this);
  }

  public void removeComment(Comment comment) {
      if (comment == null || !this.comments.contains(comment)) {
          return;
      }
    this.comments.remove(comment);
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
}