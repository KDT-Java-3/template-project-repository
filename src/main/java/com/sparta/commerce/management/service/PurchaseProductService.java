package com.sparta.commerce.management.service;

import com.sparta.commerce.management.repository.PurchaseProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@AllArgsConstructor
public class PurchaseProductService {

    private final PurchaseProductRepository purchaseProductRepository;

}
