package com.wodydtns.commerce.domain.refund.service;

import com.wodydtns.commerce.domain.refund.dto.CreateRefundRequest;
import com.wodydtns.commerce.domain.refund.dto.SearchRefundResponse;
import com.wodydtns.commerce.domain.refund.dto.UpdateRefundRequest;

public interface RefundService {

    void createRefund(CreateRefundRequest createRefundRequest);

    void updateRefund(UpdateRefundRequest updateRefundRequest);

    SearchRefundResponse searchRefund(long id);

}
