package org.umc.spring.dto.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.umc.spring.domain.enums.Role;
import org.umc.spring.validation.annotation.ExistCategories;

public class MemberRequestDTO {

  @Getter
  @Setter // @ModelAttribute를 사용하기 위해서는 @Setter 필요
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
    @NotBlank
    private String password;
    @NotNull
    private Integer point;
    @NotNull
    private Integer status; // "active", "inactive", "suspended"
    @ExistCategories
    private List<Long> preferCategory = new ArrayList<>();
    @NotNull
    private Role role;
  }

  @Getter
  @Setter
  public static class LoginRequestDTO {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수입니다.")
    private String password;
  }
}