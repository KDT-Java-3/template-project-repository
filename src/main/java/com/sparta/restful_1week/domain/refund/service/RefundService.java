package com.sparta.restful_1week.domain.refund.service;

import com.sparta.restful_1week.domain.refund.dto.RefundInDTO;
import com.sparta.restful_1week.domain.refund.dto.RefundOutDTO;
import com.sparta.restful_1week.domain.refund.entity.Refund;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefundService {
    private final Map<Long, Refund> refundMap = new HashMap<>();

    public RefundOutDTO createRefund(RefundInDTO refundInDTO) {

        // inDto -> entity
        Refund refund = new Refund(refundInDTO);

        // category max id chk
        Long maxId = refundMap.size() > 0 ? Collections.max(refundMap.keySet()) + 1 : 1;
        refund.setId(maxId);

        // db 저장
        refundMap.put(refund.getId(), refund);

        // entity -> outDto
        RefundOutDTO refundOutDTO = new RefundOutDTO(refund);

        return refundOutDTO;
    }

    public List<RefundOutDTO> getRefunds() {
        // Map to list
        List<RefundOutDTO> refundOutDTOList = refundMap.values().stream()
                .map(RefundOutDTO::new).toList();

        return refundOutDTOList;
    }

    public RefundOutDTO updateRefund(Long id, RefundInDTO refundInDTO) {
        // 해당 메모가 DB에 존재하는지 확인
        if(refundMap.containsKey(id)) {
            // 해당 ID로 카테고리 가져오기
            Refund refund = refundMap.get(id);

            // 메모 수정
            refund.updateRefund(refundInDTO);

            // entity -> outDto
            RefundOutDTO refundOutDTO = new RefundOutDTO(refund);

            return refundOutDTO;

        } else {
            throw new IllegalArgumentException("선택한 상품 데이터는 존재하지 않습니다.");
        }
    }
}
