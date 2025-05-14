package org.umc.spring.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto {
        private String name;
        private String nickname;
        private String socialId;
        private Integer socialType; // 'kakao', 'naver', 'google'
        private String phoneNumber;
        private Boolean phoneVerified;
        private Integer gender; // 'male', "female", "none"
        private String address;
        private String email;
        private Integer point;
        private Integer status; // "active", "inactive", "suspended"
        private List<Long> preferCategory = new ArrayList<>();
    }
}