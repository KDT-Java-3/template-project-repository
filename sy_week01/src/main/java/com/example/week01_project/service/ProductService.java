package com.example.week01_project.service;

import com.example.week01_project.domain.product.Product;
import com.example.week01_project.dto.product.ProductDtos.*;
import com.example.week01_project.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;

    @Transactional
    public Resp create(CreateReq req) {
        Product p = Product.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .stock(req.stock())
                .categoryId(req.categoryId())
                .isActive(true)
                .build();
        productRepo.save(p);
        return toResp(p);
    }

    @Transactional(readOnly = true)
    public Resp get(Long id) {
        Product p = productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found"));
        return toResp(p);
    }

    @Transactional(readOnly = true)
    public List<Product> list(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String keyword) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId != null) spec = spec.and((root, q, cb) -> cb.equal(root.get("categoryId"), categoryId));
        if (minPrice != null) spec = spec.and((root, q, cb) -> cb.ge(root.get("price"), minPrice));
        if (maxPrice != null) spec = spec.and((root, q, cb) -> cb.le(root.get("price"), maxPrice));
        if (keyword != null && !keyword.isBlank())
            spec = spec.and((root, q, cb) -> cb.like(root.get("name"), "%" + keyword + "%"));
        return productRepo.findAll(spec);
    }

    @Transactional
    public Resp update(Long id, UpdateReq req) {
        Product p = productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found"));
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStock(req.stock());
        p.setCategoryId(req.categoryId());
        return toResp(p);
    }

    private Resp toResp(Product p) {
        return new Resp(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStock(), p.getCategoryId(), p.getIsActive());
    }
}
