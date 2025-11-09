package com.example.week01_project.service;

import com.example.week01_project.common.ConflictException;
import com.example.week01_project.common.NotFoundException;
import com.example.week01_project.domain.category.Category;
import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.domain.product.Product;
import com.example.week01_project.dto.product.ProductRequest;
import com.example.week01_project.dto.product.ProductResponse;
import com.example.week01_project.repository.CategoryRepository;
import com.example.week01_project.repository.OrderRepository;
import com.example.week01_project.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final OrderRepository orderRepo;

    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo, OrderRepository orderRepo) {
        this.productRepo = productRepo; this.categoryRepo = categoryRepo; this.orderRepo = orderRepo;
    }

    @Transactional
    public Long create(ProductRequest req){
        if (productRepo.existsByName(req.name())) throw new ConflictException("duplicate product name");
        Category cat = categoryRepo.findById(req.categoryId()).orElseThrow(() -> new NotFoundException("category not found"));
        Product p = new Product();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStock(req.stock());
        p.setCategory(cat);
        productRepo.save(p);
        return p.getId(); // 등록 후 ID 반환 (요구사항)
    }

    public ProductResponse get(Long id){
        Product p = productRepo.findById(id).orElseThrow(() -> new NotFoundException("product not found"));
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStock(),
                p.getCategory()==null? null : p.getCategory().getId());
    }

    @Transactional
    public void update(Long id, ProductRequest req){
        Product p = productRepo.findById(id).orElseThrow(() -> new NotFoundException("product not found"));
        if (!p.getName().equals(req.name()) && productRepo.existsByName(req.name()))
            throw new ConflictException("duplicate product name");
        Category cat = categoryRepo.findById(req.categoryId()).orElseThrow(() -> new NotFoundException("category not found"));
        p.setName(req.name()); p.setDescription(req.description()); p.setPrice(req.price()); p.setStock(req.stock()); p.setCategory(cat);
    }

    @Transactional
    public void delete(Long id){
        Product p = productRepo.findById(id).orElseThrow(() -> new NotFoundException("product not found"));
        long completed = orderRepo.countByProductAndStatus(p, Orders.Status.COMPLETED);
        if (completed > 0) throw new ConflictException("cannot delete: completed order exists");
        productRepo.delete(p);
    }

    public Page<ProductResponse> search(Long categoryId, java.math.BigDecimal min, java.math.BigDecimal max,
                                        Boolean includeZero, Pageable pageable){
        return productRepo.search(categoryId, min, max, includeZero, pageable)
                .map(pp -> new ProductResponse(pp.getId(), pp.getName(), pp.getDescription(), pp.getPrice(), pp.getStock(),
                        pp.getCategory()==null? null : pp.getCategory().getId()));
    }
}
