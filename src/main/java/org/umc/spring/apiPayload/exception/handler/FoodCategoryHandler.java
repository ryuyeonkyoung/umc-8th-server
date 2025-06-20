package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.GeneralException;

public class FoodCategoryHandler extends GeneralException {

  public FoodCategoryHandler(ErrorStatus status) {
    super(status);
  }
}
