package org.umc.spring.repository.FoodCategoryRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umc.spring.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

}