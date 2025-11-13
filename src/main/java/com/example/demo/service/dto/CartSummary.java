package com.example.demo.service.dto;

import java.util.List;

public record CartSummary(List<CartItem> items, int totalQuantity, int totalPrice) {
}
