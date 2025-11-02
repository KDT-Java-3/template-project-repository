package com.sparta.week1.dto.refund;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RefundDto extends CreateRefundDto {
    private Long id;
    private Date createdAt;
}
