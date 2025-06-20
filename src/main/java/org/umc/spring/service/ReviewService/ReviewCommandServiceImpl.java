package org.umc.spring.service.ReviewService;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.handler.MemberHandler;
import org.umc.spring.apiPayload.exception.handler.StoreHandler;
import org.umc.spring.aws.s3.AmazonS3Manager;
import org.umc.spring.converter.ReviewConverter;
import org.umc.spring.domain.Member;
import org.umc.spring.domain.Review;
import org.umc.spring.domain.ReviewImage;
import org.umc.spring.domain.Store;
import org.umc.spring.domain.Uuid;
import org.umc.spring.dto.review.request.ReviewRequestDTO;
import org.umc.spring.repository.MemberRepository.MemberRepository;
import org.umc.spring.repository.ReviewRepository.ReviewRepository;
import org.umc.spring.repository.StoreRepository.StoreRepository;
import org.umc.spring.repository.UuidRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

  private final ReviewRepository reviewRepository;
  private final StoreRepository storeRepository;
  private final MemberRepository memberRepository;
  private final UuidRepository uuidRepository;
  private final AmazonS3Manager s3Manager;

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
  public Review addReviewWithImages(Long storeId, Long memberId, String context, Float rating,
      List<String> imageUrls) {
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

  @Override
  @Transactional
  public Review createReview(Long memberId, Long storeId, ReviewRequestDTO.CreateDto request,
      MultipartFile reviewImage) {

    // ReviewConverter를 통해 Review 엔티티 생성
    Review review = ReviewConverter.toReview(request);

    // 리뷰 관련 정보 설정
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new IllegalArgumentException("Store not found"));

    review.setMember(member);
    review.setStore(store);

    // 이미지가 있는 경우만 처리
    if (reviewImage != null && !reviewImage.isEmpty()) {
      // UUID 생성 및 저장
      String uuid = UUID.randomUUID().toString();
      Uuid savedUuid = uuidRepository.save(Uuid.builder()
          .uuid(uuid).build());

      // S3에 이미지 업로드
      String pictureUrl = s3Manager.uploadFile(s3Manager.generateReviewKeyName(savedUuid),
          reviewImage);

      // ReviewImage 생성 및 리뷰에 연결
      ReviewImage reviewImageEntity = ReviewImage.builder()
          .imageUrl(pictureUrl)
          .review(review)
          .build();

      // 리뷰에 이미지 추가 (cascade로 자동 저장됨)
      review.getReviewImages().add(reviewImageEntity);
    }

    // 리뷰 저장 및 반환
    return reviewRepository.save(review);
  }
}