package org.umc.spring.apiPayload.code;

public interface BaseErrorCode {

  ErrorReasonDTO getReason();

  ErrorReasonDTO getReasonHttpStatus();
}
