package org.umc.spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.umc.spring.domain.mapping.MemberPrefer;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FoodCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @OneToMany(mappedBy = "foodCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<MemberPrefer> memberPrefers = new HashSet<>();

  @Column(nullable = false, length = 50)
  private String name;

  public void addMemberPrefer(MemberPrefer memberPrefer) {
    this.memberPrefers.add(memberPrefer);
    memberPrefer.setFoodCategory(this);
  }
}