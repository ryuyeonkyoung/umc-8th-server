package org.umc.spring.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.umc.spring.domain.Review;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // SQL:
    // INSERT INTO review (store_id, member_id, context, rating, created_at)
    // VALUES ({store_id}, {member_id}, '{리뷰 내용}', {별점}, NOW());
    @Override
    public Review saveReview(Review review) {
        entityManager.persist(review);
        return review;
    }
}