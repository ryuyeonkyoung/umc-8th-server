package org.umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.MemberHandler;
import org.umc.spring.apiPayload.exception.handler.StoreHandler;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.Review;
import org.umc.spring.domain.ReviewImage;
import org.umc.spring.domain.Store;
import org.umc.spring.repository.MemberRepository.MemberRepository;
import org.umc.spring.repository.ReviewRepository.ReviewRepository;
import org.umc.spring.repository.StoreRepository.StoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Review addReview(Long storeId, Long memberId, String context, Float rating) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Review review = Review.builder()
                .store(store)
                .member(member)
                .context(context)
                .rating(rating)
                .build();

        return reviewRepository.saveReview(review);
    }

    @Override
    public Review addReviewWithImages(Long storeId, Long memberId, String context, Float rating, List<String> imageUrls) {
        Review review = addReview(storeId, memberId, context, rating);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                ReviewImage reviewImage = ReviewImage.builder()
                        .imageUrl(imageUrl)
                        .build();
                review.addReviewImage(reviewImage);
            }
        }

        return review;
    }
}