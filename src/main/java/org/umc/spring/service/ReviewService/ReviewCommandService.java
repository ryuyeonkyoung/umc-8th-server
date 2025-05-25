package org.umc.spring.service.ReviewService;

import org.umc.spring.domain.Review;

import java.util.List;

public interface ReviewCommandService {
    Review addReview(Long storeId, Long memberId, String context, Float rating);
    Review addReviewWithImages(Long storeId, Long memberId, String context, Float rating, List<String> imageUrls);
}