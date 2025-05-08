package org.umc.spring.repository.ReviewRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}