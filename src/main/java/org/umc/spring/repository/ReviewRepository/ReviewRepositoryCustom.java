package org.umc.spring.repository.ReviewRepository;

import org.umc.spring.domain.Review;

public interface ReviewRepositoryCustom {
    Long saveReview(Review review);
}