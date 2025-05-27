package org.umc.spring.service.StoreService;

import org.springframework.data.domain.Slice;
import org.umc.spring.domain.Review;
import org.umc.spring.domain.Store;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {
    Optional<Store> findStore(Long id);
    List<Store> findStoresByNameAndScore(String name, Float score);
    boolean existsById(Long id);
    Slice<Review> getReviewList(Long StoreId, Integer page);
}
