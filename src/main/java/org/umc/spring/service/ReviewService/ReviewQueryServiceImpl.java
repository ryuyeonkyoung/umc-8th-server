package org.umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.domain.Review;
import org.umc.spring.repository.ReviewRepository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

  private final ReviewRepository reviewRepository;

  @Override
  public Slice<Review> getMyReviews(Long memberId, Integer page) {
    return reviewRepository.findSliceAllByMemberId(memberId, PageRequest.of(page, 10));
  }
}

