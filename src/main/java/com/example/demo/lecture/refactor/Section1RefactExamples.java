package com.example.demo.lecture.refactor;

import java.math.BigDecimal;

/**
 * 학습 섹션 1: 리팩토링의 정의/필요성과 대표적인 코드 악취를 드러내는 예제 모음.
 */
public final class Section1RefactExamples {

    private Section1RefactExamples() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * SECTION 1 - BEFORE: 중복 배송비 계산 로직.
     * Long Method + Duplicate Code 악취를 강조하기 위해 배송비 계산 로직이 두 메서드에 그대로 복제되어 있다.
     */
    public static class PurchaseRefactServiceBefore {

        public void processNormal(PurchaseRefactAnemic purchase) {
            // SECTION 1 - THEORY: 리팩토링 전, 겉보기 동작은 같지만 내부 구조는 복잡하다.
            double shippingCost = purchase.getPrice().doubleValue() * 0.1;
            if (purchase.getWeight() > 10) {
                shippingCost += 5.0;
            }
            purchase.setShippingCost(BigDecimal.valueOf(shippingCost));
        }

        public void processUrgent(PurchaseRefactAnemic purchase) {
            // SECTION 1 - THEORY: 중복 로직은 변경 비용과 버그 가능성을 높인다.
            double shippingCost = purchase.getPrice().doubleValue() * 0.1;
            if (purchase.getWeight() > 10) {
                shippingCost += 5.0;
            }
            purchase.setShippingCost(BigDecimal.valueOf(shippingCost));
        }
    }

    /**
     * SECTION 1 - AFTER: 실습 후 정답을 직접 작성해보는 placeholder.
     * TODO: PurchaseRefactServiceBefore 코드를 리팩토링한 버전을 이 클래스에 구현해보자.
     */
    public static class PurchaseRefactServiceAfter {
        // 구현은 학습자가 직접 작성합니다.
    }

    /**
     * SECTION 1 - BEFORE ENTITY: 빈약한 도메인 모델(Anemic Domain Model) 예시.
     */
    public static class PurchaseRefactAnemic {
        private final BigDecimal price;
        private final double weight;
        private BigDecimal shippingCost = BigDecimal.ZERO;

        public PurchaseRefactAnemic(BigDecimal price, double weight) {
            this.price = price;
            this.weight = weight;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public double getWeight() {
            return weight;
        }

        public BigDecimal getShippingCost() {
            return shippingCost;
        }

        public void setShippingCost(BigDecimal shippingCost) {
            this.shippingCost = shippingCost;
        }
    }

}
