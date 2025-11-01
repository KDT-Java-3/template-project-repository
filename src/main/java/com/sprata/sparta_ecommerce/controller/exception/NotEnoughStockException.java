package com.sprata.sparta_ecommerce.controller.exception;


public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String productName, int stock, int requested) {
        super(String.format("'%s' 상품의 재고가 부족합니다. (보유: %d, 요청: %d)", productName, stock, requested));
    }
}
