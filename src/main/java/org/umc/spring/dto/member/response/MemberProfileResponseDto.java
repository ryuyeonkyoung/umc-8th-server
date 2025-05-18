package org.umc.spring.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class MemberProfileResponseDto {
    private final Long id;
    private final String nickname;
    private final String email;
    private final String phoneNumber;
    private final Boolean phoneVerified;
    private final Integer point;
}