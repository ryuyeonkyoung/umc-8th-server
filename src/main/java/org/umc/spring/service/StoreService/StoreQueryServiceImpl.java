package org.umc.spring.service.StoreService;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umc.spring.domain.Review;
import org.umc.spring.domain.Store;
import org.umc.spring.repository.ReviewRepository.ReviewRepository;
import org.umc.spring.repository.StoreRepository.StoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService {

  private final StoreRepository storeRepository;
  private final ReviewRepository reviewRepository;

  @Override
  public Optional<Store> findStore(Long id) {
    return storeRepository.findById(id);
  }

  @Override
  public List<Store> findStoresByNameAndScore(String name, Float score) {
    List<Store> filteredStores = storeRepository.dynamicQueryWithBooleanBuilder(name, score);

    filteredStores.forEach(store -> System.out.println("Store: " + store));

    return filteredStores;
  }

  @Override
  public boolean existsById(Long id) {
    return storeRepository.existsById(id);
  }

  @Override
  public Slice<Review> getReviewList(Long StoreId, Integer page) {

    Store store = storeRepository.findById(StoreId).get();

    Page<Review> StorePage = reviewRepository.findAllByStore(store, PageRequest.of(page, 10));
    return StorePage;
  }
}