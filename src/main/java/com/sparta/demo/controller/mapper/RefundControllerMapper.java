package com.sparta.demo.controller.mapper;

import com.sparta.demo.controller.dto.refund.RefundRequest;
import com.sparta.demo.controller.dto.refund.RefundResponse;
import com.sparta.demo.service.dto.refund.RefundCreateDto;
import com.sparta.demo.service.dto.refund.RefundDto;
import org.springframework.stereotype.Component;

/**
 * Refund Controller Layer Mapper
 * Request → Service DTO, Service DTO → Response 변환 담당
 */
@Component
public class RefundControllerMapper {

    /**
     * RefundRequest를 RefundCreateDto로 변환
     */
    public RefundCreateDto toCreateDto(RefundRequest request) {
        return new RefundCreateDto(
                request.getOrderId(),
                request.getUserId(),
                request.getReason()
        );
    }

    /**
     * RefundDto를 RefundResponse로 변환
     */
    public RefundResponse toResponse(RefundDto dto) {
        return new RefundResponse(
                dto.getId(),
                dto.getOrderId(),
                dto.getUserId(),
                dto.getReason(),
                dto.getStatus(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
