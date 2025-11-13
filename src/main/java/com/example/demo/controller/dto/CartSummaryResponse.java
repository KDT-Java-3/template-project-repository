package com.example.demo.controller.dto;

import java.util.List;

public record CartSummaryResponse(List<CartItemResponse> items, int totalQuantity, int totalPrice) {
}
