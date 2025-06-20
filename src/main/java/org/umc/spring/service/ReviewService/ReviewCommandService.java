package org.umc.spring.service.ReviewService;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.umc.spring.domain.Review;
import org.umc.spring.dto.review.request.ReviewRequestDTO;

public interface ReviewCommandService {

  Review addReview(Long storeId, Long memberId, String context, Float rating);

  Review addReviewWithImages(Long storeId, Long memberId, String context, Float rating,
      List<String> imageUrls);

  Review createReview(Long memberId, Long storeId, ReviewRequestDTO.CreateDto request,
      MultipartFile reviewImage);
}