package com.example.demo.lecture.refactor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * SECTION 1 - Advanced: 메서드 추출/조건 캡슐화/데이터 클래스를 활용한 심화 리팩토링 예제 모음.
 * 각 BEFORE 클래스는 의도적으로 복잡하게 작성되었고, AFTER 클래스는 학습자가 직접 채우도록 비워 두었다.
 */
public final class Section1AdvancedRefactExamples {

    private Section1AdvancedRefactExamples() {
        throw new IllegalStateException("Utility class");
    }

    // ============================================================
    // ADVANCED EXAMPLE 1: 거대한 주문 처리 메서드
    // ============================================================

    /**
     * SECTION 1 ADVANCED - BEFORE:
     * - 하나의 메서드가 할인/배송/포인트 적립/로그 기록까지 모두 처리한다.
     * - 복잡한 조건문과 중복된 계산을 포함하여 Long Method, Feature Envy 악취가 혼재되어 있다.
     */
    public static class BulkPurchaseRefactBefore {

        public AdvancedPurchase process(AdvancedPurchase purchase) {
            // 할인 계산 (조건이 겹침)
            BigDecimal subtotal = BigDecimal.ZERO;
            for (AdvancedPurchaseItem item : purchase.getItems()) {
                BigDecimal linePrice = item.price().multiply(BigDecimal.valueOf(item.quantity()));
                subtotal = subtotal.add(linePrice);
            }

            BigDecimal discount = BigDecimal.ZERO;
            if (subtotal.compareTo(BigDecimal.valueOf(500_000)) > 0) {
                discount = subtotal.multiply(BigDecimal.valueOf(0.15));
            } else if (subtotal.compareTo(BigDecimal.valueOf(200_000)) > 0) {
                discount = subtotal.multiply(BigDecimal.valueOf(0.07));
            } else if ("VIP".equals(purchase.getCustomerGrade())) {
                discount = subtotal.multiply(BigDecimal.valueOf(0.05));
            }
            subtotal = subtotal.subtract(discount);

            // 배송비 계산과 중복된 로직
            BigDecimal shippingFee = BigDecimal.valueOf(3_500);
            if (subtotal.compareTo(BigDecimal.valueOf(300_000)) > 0) {
                shippingFee = BigDecimal.ZERO;
            }
            if (purchase.isOverseas()) {
                shippingFee = shippingFee.add(BigDecimal.valueOf(15_000));
            }
            purchase.setShippingFee(shippingFee);

            // 포인트 적립 + 로그
            int point = subtotal.divide(BigDecimal.valueOf(10_000)).intValue() * 10;
            purchase.setEarnedPoint(point);
            System.out.printf("[BulkPurchase] user=%s subtotal=%s discount=%s point=%d%n",
                    purchase.getCustomerId(), subtotal, discount, point);

            purchase.setFinalPrice(subtotal.add(shippingFee));
            purchase.setProcessedAt(LocalDate.now());
            return purchase;
        }
    }

    /**
     * SECTION 1 ADVANCED - AFTER Placeholder:
     * TODO: 위 로직을 할인 계산, 배송비 계산, 포인트 적립, 감사 로그 등으로 분리해 구현해보자.
     */
    public static class BulkPurchaseRefactAfter {
        // 학습자가 직접 리팩토링 코드를 작성한다.
    }

    // ============================================================
    // ADVANCED EXAMPLE 2: 쿠폰 검증 로직
    // ============================================================

    /**
     * SECTION 1 ADVANCED - BEFORE:
     * - 여러 조건이 중첩되고 중복되어 있어 읽기 어렵다.
     * - 조건 캡슐화(Extract Method, Introduce Parameter Object) 연습용.
     */
    public static class CouponValidationRefactBefore {

        public boolean isValidCoupon(AdvancedCoupon coupon, AdvancedPurchase purchase) {
            if (coupon == null) {
                return false;
            }
            if (coupon.getExpiredAt().isBefore(LocalDate.now())) {
                return false;
            }
            if (!coupon.getAvailableGrades().contains(purchase.getCustomerGrade())) {
                return false;
            }
            if (purchase.getItems().isEmpty()) {
                return false;
            }
            BigDecimal total = BigDecimal.ZERO;
            for (AdvancedPurchaseItem item : purchase.getItems()) {
                total = total.add(item.price().multiply(BigDecimal.valueOf(item.quantity())));
            }
            if (coupon.getMinimumAmount() != null && total.compareTo(coupon.getMinimumAmount()) < 0) {
                return false;
            }
            if (coupon.isOnlyForOverseas() && !purchase.isOverseas()) {
                return false;
            }
            return true;
        }
    }

    /**
     * SECTION 1 ADVANCED - AFTER Placeholder:
     * TODO: 조건 절을 명확한 private 메서드로 추출하고, 총액 계산 중복을 제거해보자.
     */
    public static class CouponValidationRefactAfter {
        // 학습자가 직접 리팩토링 코드를 작성한다.
    }

    // ============================================================
    // ADVANCED EXAMPLE 3: 반복되는 다단계 요금 계산
    // ============================================================

    /**
     * SECTION 1 ADVANCED - BEFORE:
     * - 여러 곳에서 사용되는 요금 계산 정책이 한 메서드에 뒤섞여 있다.
     * - 향후 정책 변경에 취약하므로 전략/정책 객체로 분리하는 연습을 한다.
     */
    public static class TieredFeeCalculatorRefactBefore {

        public BigDecimal calculateFee(AdvancedPurchase purchase) {
            BigDecimal fee = BigDecimal.ZERO;
            for (AdvancedPurchaseItem item : purchase.getItems()) {
                BigDecimal base = item.price().multiply(BigDecimal.valueOf(item.quantity()));
                BigDecimal tierFee;
                if (base.compareTo(BigDecimal.valueOf(300_000)) > 0) {
                    tierFee = base.multiply(BigDecimal.valueOf(0.12));
                } else if (base.compareTo(BigDecimal.valueOf(100_000)) > 0) {
                    tierFee = base.multiply(BigDecimal.valueOf(0.08));
                } else {
                    tierFee = base.multiply(BigDecimal.valueOf(0.03));
                }
                if (purchase.isOverseas()) {
                    tierFee = tierFee.add(BigDecimal.valueOf(5_000));
                }
                fee = fee.add(tierFee);
            }
            if ("VIP".equals(purchase.getCustomerGrade())) {
                fee = fee.multiply(BigDecimal.valueOf(0.9));
            }
            return fee;
        }
    }

    /**
     * SECTION 1 ADVANCED - AFTER Placeholder:
     * TODO: 요금 정책을 별도 Strategy로 분리하거나, 함수형으로 주입해보자.
     */
    public static class TieredFeeCalculatorRefactAfter {
        // 학습자가 직접 리팩토링 코드를 작성한다.
    }

    // ============================================================
    // ADVANCED SUPPORTING MODEL
    // ============================================================

    public static class AdvancedPurchase {
        private final String customerId;
        private final String customerGrade;
        private final boolean overseas;
        private final List<AdvancedPurchaseItem> items = new ArrayList<>();
        private BigDecimal finalPrice = BigDecimal.ZERO;
        private BigDecimal shippingFee = BigDecimal.ZERO;
        private int earnedPoint;
        private LocalDate processedAt;

        public AdvancedPurchase(String customerId, String customerGrade, boolean overseas) {
            this.customerId = customerId;
            this.customerGrade = customerGrade;
            this.overseas = overseas;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getCustomerGrade() {
            return customerGrade;
        }

        public boolean isOverseas() {
            return overseas;
        }

        public List<AdvancedPurchaseItem> getItems() {
            return items;
        }

        public void addItem(AdvancedPurchaseItem item) {
            items.add(item);
        }

        public void setFinalPrice(BigDecimal finalPrice) {
            this.finalPrice = finalPrice;
        }

        public void setShippingFee(BigDecimal shippingFee) {
            this.shippingFee = shippingFee;
        }

        public void setEarnedPoint(int earnedPoint) {
            this.earnedPoint = earnedPoint;
        }

        public void setProcessedAt(LocalDate processedAt) {
            this.processedAt = processedAt;
        }
    }

    public record AdvancedPurchaseItem(String sku, BigDecimal price, int quantity) {
    }

    public static class AdvancedCoupon {
        private final LocalDate expiredAt;
        private final List<String> availableGrades;
        private final BigDecimal minimumAmount;
        private final boolean onlyForOverseas;

        public AdvancedCoupon(LocalDate expiredAt, List<String> availableGrades, BigDecimal minimumAmount, boolean onlyForOverseas) {
            this.expiredAt = expiredAt;
            this.availableGrades = availableGrades;
            this.minimumAmount = minimumAmount;
            this.onlyForOverseas = onlyForOverseas;
        }

        public LocalDate getExpiredAt() {
            return expiredAt;
        }

        public List<String> getAvailableGrades() {
            return availableGrades;
        }

        public BigDecimal getMinimumAmount() {
            return minimumAmount;
        }

        public boolean isOnlyForOverseas() {
            return onlyForOverseas;
        }
    }
}
