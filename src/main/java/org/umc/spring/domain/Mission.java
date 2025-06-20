package org.umc.spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.umc.spring.domain.common.BaseEntity;
import org.umc.spring.domain.mapping.MemberMission;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Mission extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<MemberMission> memberMissions = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @Lob
  @Column(nullable = false)
  private String missionSpec;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 1000")
  private Integer minSpendMoney;

  @Column(nullable = false)
  private Integer rewardPoints;

  @Column(nullable = false, length = 50)
  private String address;

  @Column(nullable = false)
  private LocalDate deadline;

  @Version
  @Builder.Default
  @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
  private Long version = 0L;

  public void addMemberMission(MemberMission memberMission) {
      if (memberMission == null || !this.memberMissions.contains(memberMission)) {
          return;
      }
    this.memberMissions.add(memberMission);
    memberMission.setMission(this);
  }

  public void removeMemberMission(MemberMission memberMission) {
      if (memberMission == null || !this.memberMissions.contains(memberMission)) {
          return;
      }
    this.memberMissions.remove(memberMission);
    memberMission.setMission(null);
  }

}