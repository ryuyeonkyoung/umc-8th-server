package org.umc.spring.repository.ReviewRepository;

import org.umc.spring.domain.Review;

public interface ReviewRepositoryCustom {

  Review saveReview(Review review);
}