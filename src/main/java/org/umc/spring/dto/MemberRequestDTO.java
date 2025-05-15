package org.umc.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.umc.spring.validation.annotation.ExistCategories;

import java.util.ArrayList;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto {
        @NotBlank
        private String name;
        @NotNull
        private String nickname;
        @NotNull
        private String socialId;
        @NotNull
        private Integer socialType; // 'kakao', 'naver', 'google'
        @NotNull
        private String phoneNumber;
        @NotNull
        private Boolean phoneVerified;
        @NotNull
        private Integer gender; // 'male', "female", "none"
        @Size(min = 5, max = 12)
        private String address;
        @NotNull
        private String email;
        @NotNull
        private Integer point;
        @NotNull
        private Integer status; // "active", "inactive", "suspended"
        @ExistCategories
        private List<Long> preferCategory = new ArrayList<>();
    }
}