package com.sparta.jc.domain.product.converter.component;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * ProductConverter 매핑 헬퍼
 */
@Component
public class ProductConvertingHelper {

    /**
     * 상품_설명 변환: 상품_설명 문자열 맨앞과 뒤에 불필요한 문자열이 있으면 제거.
     */
    @Named("DescriptionConverter")
    public String convertDescription(String description) {
        if (description == null) {
            return null;
        }
        String trimmed = description.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

}
