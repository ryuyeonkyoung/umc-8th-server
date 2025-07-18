package org.umc.spring.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.umc.spring.apiPayload.code.BaseErrorCode;
import org.umc.spring.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

  // 가장 일반적인 응답
  _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
  _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
  _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
  _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


  // 도메인 관련 에러
  MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
  NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

  FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD4003", "음식 카테고리가 없습니다."),

  REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION4004", "지역이 없습니다."),

  STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE4005", "가게가 없습니다."),

  MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4001", "미션을 찾을 수 없습니다."),
  MISSION_ALREADY_CHALLENGED(HttpStatus.BAD_REQUEST, "MISSION4002", "이미 도전 중인 미션입니다."),
  MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "MISSION4003", "이미 완료된 미션입니다."),
  MEMBER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4004", "해당 회원의 미션을 찾을 수 없습니다."),

  // 조회 관련 에러
  PAGE_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 번호는 1 이상이어야 합니다."),
  PAGE_NO_DATA(HttpStatus.BAD_REQUEST, "PAGE4002", "해당 페이지에 데이터가 없습니다."),

  // 인증 관련 에러
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER4003", "패스워드가 불일치합니다."),
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "MEMBER4005", "유효하지 않은 토큰입니다."),

  // 예시,,,
  ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),
  // For test
  TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  @Override
  public ErrorReasonDTO getReason() {
    return ErrorReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(false)
        .build();
  }

  @Override
  public ErrorReasonDTO getReasonHttpStatus() {
    return ErrorReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(false)
        .httpStatus(httpStatus)
        .build()
        ;
  }
}
