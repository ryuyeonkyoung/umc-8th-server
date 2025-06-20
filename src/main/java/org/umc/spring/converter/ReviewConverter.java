package org.umc.spring.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Slice;
import org.umc.spring.domain.Review;
import org.umc.spring.domain.enums.ReviewStatus;
import org.umc.spring.dto.review.request.ReviewRequestDTO;
import org.umc.spring.dto.review.response.ReviewResponseDTO;

public class ReviewConverter {

  public static ReviewResponseDTO.CreateResultDTO toCreateResultDTO(Review review) {
    return ReviewResponseDTO.CreateResultDTO.builder()
        .reviewId(review.getId())
        .content(review.getContext())
        .rating(review.getRating())
        .createdAt(review.getCreatedAt())
//                .imageUrls(review.getreviewImages().stream()
//                        .map(ReviewImage::getImageUrl)
//                        .collect(Collectors.toList()))
        .build();
  }

  public static Review toReview(ReviewRequestDTO.CreateDto createDto) {
    return Review.builder()
        .context(createDto.getContent())
        .rating(createDto.getRating())
        .comments(new HashSet<>())
        .reviewImages(new ArrayList<>())
        .status(ReviewStatus.ACTIVE)
        .build();
  }

  public static ReviewResponseDTO.MyReviewPreviewDTO toMyReviewPreviewDTO(Review review) {
    return ReviewResponseDTO.MyReviewPreviewDTO.builder()
        .reviewId(review.getId())
        .storeName(review.getStore().getName())
        .content(review.getContext())
        .rating(review.getRating())
        .createdAt(review.getCreatedAt().toLocalDate())
        .build();
  }

  public static ReviewResponseDTO.MyReviewListDTO toMyReviewListDTO(Slice<Review> reviewSlice) {
    List<ReviewResponseDTO.MyReviewPreviewDTO> reviewPreviewList = reviewSlice.stream()
        .map(ReviewConverter::toMyReviewPreviewDTO)
        .collect(Collectors.toList());

    return ReviewResponseDTO.MyReviewListDTO.builder()
        .reviewList(reviewPreviewList)
        .listSize(reviewPreviewList.size())
        .totalPage(null) // Slice에서는 전체 페이지 수를 알 수 없음
        .totalElements(null) // Slice에서는 전체 요소 수를 알 수 없음
        .isFirst(reviewSlice.isFirst())
        .isLast(reviewSlice.isLast())
        .hasNext(reviewSlice.hasNext()) // hasNext 추가
        .build();
  }
}