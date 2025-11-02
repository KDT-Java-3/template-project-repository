package com.pepponechoi.project.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDeleteRequest {
    @NotBlank(message = "삭제 할 주문의 ID는 필수입니다.")
    Long id;
    @NotBlank(message = "삭제할 유저의 ID는 필수입니다.")
    Long userId; // 원래는 JWT 이용하여 유저 ID 파싱해야 함.
}
