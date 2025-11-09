package com.sprata.sparta_ecommerce.dto.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchOrderDto {
    private String orderStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate endDate;

    @AssertTrue(message = "시작일은 종료일보다 이후일 수 없습니다.")
    private boolean isValidDateRange() {
        if (startDate == null || endDate == null) {
            return true; // null은 별도 NotNull 검증에서 처리하도록
        }
        return !startDate.isAfter(endDate);
    }
}
