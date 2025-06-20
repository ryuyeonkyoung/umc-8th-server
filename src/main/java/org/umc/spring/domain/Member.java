package org.umc.spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.umc.spring.domain.enums.Gender;
import org.umc.spring.domain.enums.MemberStatus;
import org.umc.spring.domain.enums.Role;
import org.umc.spring.domain.enums.SocialType;
import org.umc.spring.domain.mapping.MemberAgree;
import org.umc.spring.domain.mapping.MemberMission;
import org.umc.spring.domain.mapping.MemberPrefer;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
  private Long version = 0L;

  @Builder.Default
  @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<MemberAgree> memberAgrees = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<MemberPrefer> memberPrefers = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "member", orphanRemoval = true)
  private Set<MemberMission> memberMissions = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Review> reviews = new HashSet<>();

  @Column(nullable = false, length = 20)
  private String name;

  @Column(nullable = false, length = 20)
  private String nickname;

  @Column(nullable = false, length = 20)
  private String socialId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10)")
  private SocialType socialType;

  @Column(nullable = false, length = 20)
  private String phoneNumber;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean phoneVerified;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10)")
  private Gender gender;

  @Column(nullable = false, length = 50)
  private String address;

  @Column(nullable = false, length = 50)
  private String email;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(nullable = false, length = 20)
  private Integer point = 0;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
  private Role role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
  private MemberStatus status;

  private LocalDate inactiveDate;

  public void encodePassword(String password) {
    this.password = password;
  }

  public void addMemberPrefer(MemberPrefer memberPrefer) {
      if (memberPrefer == null || memberPrefers.contains(memberPrefer)) {
          return;
      }
    this.memberPrefers.add(memberPrefer);
    memberPrefer.setMember(this);
  }

  public void addMemberAgree(MemberAgree memberAgree) {
      if (memberAgree == null || memberAgrees.contains(memberAgree)) {
          return;
      }
    this.memberAgrees.add(memberAgree);
    memberAgree.setMember(this);
  }

  public void addMemberMission(MemberMission memberMission) {
      if (memberMission == null || memberMissions.contains(memberMission)) {
          return;
      }
    this.memberMissions.add(memberMission);
    memberMission.setMember(this);
  }

  public void addReview(Review review) {
      if (review == null || reviews.contains(review)) {
          return;
      }
    this.reviews.add(review);
    review.setMember(this);
  }

  public void removeMemberPrefer(MemberPrefer memberPrefer) {
      if (memberPrefer == null || !this.memberPrefers.contains(memberPrefer)) {
          return;
      }
    this.memberPrefers.remove(memberPrefer);
    memberPrefer.setMember(null);
  }

  public void removeMemberAgree(MemberAgree memberAgree) {
      if (memberAgree == null || !this.memberAgrees.contains(memberAgree)) {
          return;
      }
    this.memberAgrees.remove(memberAgree);
    memberAgree.setMember(null);
  }

  public void removeMemberMission(MemberMission memberMission) {
      if (memberMission == null || !this.memberMissions.contains(memberMission)) {
          return;
      }
    this.memberMissions.remove(memberMission);
    memberMission.setMember(null);
  }

  public void removeReview(Review review) {
      if (review == null || !this.reviews.contains(review)) {
          return;
      }
    this.reviews.remove(review);
    review.setMember(null);
  }

}