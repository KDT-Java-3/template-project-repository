package com.example.demo.lecture.refactorsection1;

import java.math.BigDecimal;

public record RefactorSection1OrderLine(Long productId, BigDecimal unitPrice, int quantity) {
}
