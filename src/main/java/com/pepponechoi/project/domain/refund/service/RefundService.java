package com.pepponechoi.project.domain.refund.service;

import com.pepponechoi.project.domain.refund.dto.request.RefundCreateRequest;
import com.pepponechoi.project.domain.refund.dto.request.RefundReadRequest;
import com.pepponechoi.project.domain.refund.dto.request.RefundUpdateRequest;
import com.pepponechoi.project.domain.refund.dto.response.RefundResponse;
import java.util.List;

public interface RefundService {
    RefundResponse createRefund(RefundCreateRequest request);
    List<RefundResponse> getRefundsByUser(RefundReadRequest user);
    Boolean update(Long id, RefundUpdateRequest request);
}
