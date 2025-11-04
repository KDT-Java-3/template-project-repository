package com.example.stproject.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    /*
    ### 3. **주문 관리 (Order Management)**
    **주문 생성 API**
    - 사용자가 장바구니에 담은 상품을 주문할 수 있는 API를 구현합니다.
    - 필수 입력 필드: `user_id`, `product_id`, `quantity`, `shipping_address`.
    - 주문 생성 시, 상품 재고를 확인하고 감소 처리.

    **주문 조회 API**
    - 특정 사용자의 주문 목록을 조회할 수 있는 API를 구현합니다.
    - 조회 가능한 정보: 주문 상태(`pending`, `completed`, `canceled`), 주문 날짜, 상품 상세.

    **주문 상태 변경 API**
    - 주문의 상태를 업데이트할 수 있는 API를 구현합니다.
    - 상태 변경 가능 범위: `pending` → `completed` / `canceled`.

    **주문 취소 API**
    - 사용자가 주문을 취소할 수 있는 API를 구현합니다.
    - 취소 조건: `pending` 상태의 주문만 취소 가능.
    * */
}
