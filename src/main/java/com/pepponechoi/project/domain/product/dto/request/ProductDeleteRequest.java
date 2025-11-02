package com.pepponechoi.project.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDeleteRequest {
    @NotBlank(message = "삭제 할 제품의 ID는 필수입니다.")
    Long id;
    @NotBlank(message = "삭제할 유저의 ID는 필수입니다.")
    Long userId; // 원래는 JWT 이용하여 유저 ID 파싱해야 함.
}
