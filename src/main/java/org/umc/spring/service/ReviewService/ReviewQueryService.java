package org.umc.spring.service.ReviewService;

import org.springframework.data.domain.Page;
import org.umc.spring.domain.Review;

public interface ReviewQueryService {
    Page<Review> getMyReviews(Long memberId, Integer page);
}