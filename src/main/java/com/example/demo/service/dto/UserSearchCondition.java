package com.example.demo.service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSearchCondition {
    String nameKeyword;
    String emailKeyword;
    LocalDateTime createdAfter;
    LocalDateTime createdBefore;
    Integer minPurchaseCount;
}
