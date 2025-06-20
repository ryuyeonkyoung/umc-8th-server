package org.umc.spring.apiPayload.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {

  private final boolean isSuccess;
  private final String code;
  private final String message;
  private HttpStatus httpStatus;

  public boolean getIsSuccess() {
    return isSuccess;
  }
}
