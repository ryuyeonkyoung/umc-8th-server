package org.umc.spring.domain.mapping;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.umc.spring.domain.FoodCategory;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPrefer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "food_category_id", nullable = false)
  private FoodCategory foodCategory;

  public void setMember(Member member) {
      if (this.member != null) {
          member.getMemberPrefers().remove(this);
      }
    this.member = member;
  }

  public void setFoodCategory(FoodCategory foodCategory) {
      if (this.foodCategory != null) {
          foodCategory.getMemberPrefers().remove(this);
      }
    this.foodCategory = foodCategory;
  }
}