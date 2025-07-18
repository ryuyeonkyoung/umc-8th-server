package org.umc.spring.dto.store.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Getter;

public class StoreRequestDTO {

  @Getter
  public static class CreateDto {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String address;

    @NotNull
    private Float score;

    @NotNull
    private DayOfWeek closedDay;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:dd", timezone = "Asia/Seoul")
    private LocalTime openTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:dd", timezone = "Asia/Seoul")
    private LocalTime closeTime;
  }
}