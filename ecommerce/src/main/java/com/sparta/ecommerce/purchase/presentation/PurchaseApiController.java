package com.sparta.ecommerce.purchase.presentation;

import static com.sparta.ecommerce.purchase.application.dto.PurchaseDto.*;

import com.sparta.ecommerce.purchase.application.PurchaseService;
import com.sparta.ecommerce.purchase.domain.PurchaseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/purchases")
@RestController
@RequiredArgsConstructor
public class PurchaseApiController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase
    (
        @RequestBody @Valid PurchaseCreateRequest createRequest
    ) {
        PurchaseResponse result = purchaseService.createPurchase(createRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getUserPurchases(
        @RequestParam Long userId
    ) {
        List<PurchaseResponse> result = purchaseService.getUserPurchases(userId);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<PurchaseResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam PurchaseStatus status
    ){
        PurchaseResponse result = purchaseService.changeStatus(id, status);
        return ResponseEntity.ok(result);
    }

}
