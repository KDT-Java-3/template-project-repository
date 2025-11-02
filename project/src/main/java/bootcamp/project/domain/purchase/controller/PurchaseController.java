package bootcamp.project.domain.purchase.controller;

import bootcamp.project.domain.purchase.dto.CreatePurchaseDto;
import bootcamp.project.domain.purchase.entity.PurchaseProduct;
import bootcamp.project.domain.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/create")
    public String addCart(@RequestBody CreatePurchaseDto createPurchaseDto) {
        purchaseService.addCart(createPurchaseDto);
        return "success";
    }

    @GetMapping("/serach")
    public List<PurchaseProduct> search(@RequestParam Long purchaseId) {
        return purchaseService.searchPurchaseProduct( purchaseId);
    }
}
