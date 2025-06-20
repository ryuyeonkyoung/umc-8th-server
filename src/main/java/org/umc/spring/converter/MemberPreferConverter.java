package org.umc.spring.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.umc.spring.domain.FoodCategory;
import org.umc.spring.domain.mapping.MemberPrefer;

public class MemberPreferConverter {

  public static List<MemberPrefer> toMemberPreferList(List<FoodCategory> foodCategoryList) {

    return foodCategoryList.stream()
        .map(foodCategory ->
            MemberPrefer.builder()
                .foodCategory(foodCategory)
                .build()
        ).collect(Collectors.toList());
  }
}