package org.umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.service.StoreService.StoreQueryService;
import org.umc.spring.validation.annotation.ExistStore;

@Component
@RequiredArgsConstructor
public class StoreExistValidator implements ConstraintValidator<ExistStore, Long> {

  private final StoreQueryService storeQueryService;

  @Override
  public void initialize(ExistStore constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Long storeId, ConstraintValidatorContext context) {
    boolean isValid = storeQueryService.existsById(storeId);

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(ErrorStatus.STORE_NOT_FOUND.toString())
          .addConstraintViolation();
    }

    return isValid;
  }
}