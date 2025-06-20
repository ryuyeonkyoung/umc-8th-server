package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.GeneralException;

public class StoreHandler extends GeneralException {

  public StoreHandler(ErrorStatus status) {
    super(status);
  }
}
