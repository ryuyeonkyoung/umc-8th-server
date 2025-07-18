package org.umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.service.FoodCategoryService.FoodCategoryService;
import org.umc.spring.validation.annotation.ExistCategories;

@Component
@RequiredArgsConstructor
public class CategoriesExistValidator implements ConstraintValidator<ExistCategories, List<Long>> {

  private final FoodCategoryService foodCategoryService;


  @Override
  public void initialize(ExistCategories constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
    boolean isValid = values.stream()
        .allMatch(value -> foodCategoryService.existsById(value));

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(ErrorStatus.FOOD_CATEGORY_NOT_FOUND.toString())
          .addConstraintViolation();
    }

    return isValid;

  }
}