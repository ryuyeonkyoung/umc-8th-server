package org.umc.spring.service.ReviewService;

import org.springframework.data.domain.Slice;
import org.umc.spring.domain.Review;

public interface ReviewQueryService {

  Slice<Review> getMyReviews(Long memberId, Integer page);
}

