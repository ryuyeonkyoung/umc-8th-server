package org.umc.spring.dto.review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO {
        private Long reviewId;
        private String content;
        private Float rating;
        private LocalDateTime createdAt;
        private List<String> imageUrls;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewPreviewDTO {
        private Long reviewId;
        private String storeName;
        private String content;
        private Float rating;
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewListDTO {
        private List<MyReviewPreviewDTO> reviewList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
        private Boolean hasNext; // 다음 페이지 존재 여부 표시 필드 추가
    }
}