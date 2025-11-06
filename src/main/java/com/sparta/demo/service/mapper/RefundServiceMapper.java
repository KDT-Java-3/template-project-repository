package com.sparta.demo.service.mapper;

import com.sparta.demo.domain.refund.Refund;
import com.sparta.demo.service.dto.refund.RefundDto;
import org.springframework.stereotype.Component;

/**
 * Refund Service Layer Mapper
 * Entity → DTO 변환 담당
 */
@Component
public class RefundServiceMapper {

    /**
     * Refund Entity를 RefundDto로 변환
     */
    public RefundDto toDto(Refund refund) {
        return new RefundDto(
                refund.getId(),
                refund.getOrder().getId(),
                refund.getUser().getId(),
                refund.getReason(),
                refund.getStatus(),
                refund.getCreatedAt(),
                refund.getUpdatedAt()
        );
    }
}
