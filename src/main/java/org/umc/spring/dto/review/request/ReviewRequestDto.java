package org.umc.spring.dto.review.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ReviewRequestDto {
    private final String content;
    private final Double rating;
    private final Long memberId;
    private final Long storeId;
}
