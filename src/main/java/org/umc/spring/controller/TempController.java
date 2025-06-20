package org.umc.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umc.spring.apiPayload.ApiResponse;
import org.umc.spring.converter.TempConverter;
import org.umc.spring.dto.TempResponse;
import org.umc.spring.service.TempService.TempQueryService;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempController {

  private final TempQueryService tempQueryService;

  @GetMapping("/test")
  public ApiResponse<TempResponse.TempTestDTO> testAPI() {

    return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
  }

  @GetMapping("/exception")
  public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag) {
    tempQueryService.CheckFlag(flag);
    return ApiResponse.onSuccess(TempConverter.toTempExceptionDTO(flag));
  }

  @GetMapping("/500-error")
  public void forceError() {
    throw new RuntimeException(" [mission07]  500 에러 발생");
  }

}
