package com.study.shop.domain.service;

import com.study.shop.domain.dto.ProductDto;
import com.study.shop.domain.entity.Category;
import com.study.shop.domain.entity.Product;
import com.study.shop.domain.repository.CategoryRepository;
import com.study.shop.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductDto.Resp create(ProductDto.CreateProduct request) throws IllegalAccessException {
        Category cat = categoryRepository.findById(request.getCategoryId())
                .orElseThrow( () -> new IllegalAccessException("없는 카테고리입니다."));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(cat)
                .build();

       Product saveProduct = productRepository.save(product);

        return ProductDto.Resp.of(saveProduct);
    }

    @Transactional
    public ProductDto.Resp update(Long id, ProductDto.UpdateProduct request) throws IllegalAccessException {
        Product product1 = productRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("상품이 없습니다."));
        if (request.getName() != null) {
            product1.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product1.setDescription(request.getDescription());
        }
        if (product1.getPrice() != null) {
            product1.setPrice(request.getPrice());
        }

        if (product1.getStock() != null) {
            product1.setStock(request.getStock());
        }

        if (product1.getCategory() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다."));
            product1.setCategory(category);
        }
        return ProductDto.Resp.of(product1);
    }

    @Transactional(readOnly = true)
    public List<ProductDto.Resp> search(ProductDto.SearchProduct request) {
        Specification<Product> spec = (root, q, cb) -> {
            List<Predicate> ps = new ArrayList<>();
            if (request.getCategoryId() != null)
                ps.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
            if (request.getMinPrice() != null)
                ps.add(cb.ge(root.get("price"), request.getMinPrice()));
            if (request.getMaxPrice() != null)
                ps.add(cb.le(root.get("price"), request.getMaxPrice()));
            if (request.getKeyword() != null && !request.getKeyword().isBlank())
                ps.add(cb.like(cb.lower(root.get("name")), "%" + request.getKeyword().toLowerCase() + "%"));
            return cb.and(ps.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec).stream()
                .map(ProductDto.Resp::of)
                .toList();
    }


}
