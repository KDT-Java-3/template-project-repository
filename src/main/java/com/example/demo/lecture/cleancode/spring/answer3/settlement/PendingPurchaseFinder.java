package com.example.demo.lecture.cleancode.spring.answer3.settlement;

import com.example.demo.PurchaseStatus;
import com.example.demo.entity.Purchase;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PendingPurchaseFinder {

    private final PurchaseRepository purchaseRepository;

    public PendingPurchaseFinder(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> findBatch(int size) {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> purchase.getStatus() == PurchaseStatus.PENDING)
                .sorted(Comparator.comparing(Purchase::getPurchasedAt))
                .limit(size)
                .toList();
    }
}
