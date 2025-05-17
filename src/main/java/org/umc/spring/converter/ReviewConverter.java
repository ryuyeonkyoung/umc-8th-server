package org.umc.spring.converter;

import org.umc.spring.domain.Review;
import org.umc.spring.domain.enums.ReviewStatus;
import org.umc.spring.dto.review.request.ReviewRequestDTO;
import org.umc.spring.dto.review.response.ReviewResponseDTO;

import java.util.ArrayList;
import java.util.HashSet;

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
}