package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.enums.Gender;
import org.umc.spring.domain.enums.MemberStatus;
import org.umc.spring.domain.enums.SocialType;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String phoneNumber;

    private Boolean phoneVerified;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private String email;

    private Integer point = 0; // 초기값 설정

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDate inactiveDate;
}