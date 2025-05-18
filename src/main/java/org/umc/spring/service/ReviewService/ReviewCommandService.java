package org.umc.spring.service.ReviewService;

public interface ReviewCommandService {
    boolean setStoreReviews(Long storeId, Long memberId, String context, Float rating);
}