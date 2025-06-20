package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.GeneralException;

public class RegionHandler extends GeneralException {

  public RegionHandler(ErrorStatus status) {
    super(status);
  }
}
