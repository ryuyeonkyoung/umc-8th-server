package org.umc.spring.dto.store.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StoreResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDto {
        private Long id;
        private Long regionId;
        private String name;
        private String address;
        private Float score;
        private DayOfWeek closedDay;
        private LocalTime openTime;
        private LocalTime closeTime;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}