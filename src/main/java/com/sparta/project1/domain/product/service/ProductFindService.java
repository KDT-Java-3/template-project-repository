package com.sparta.project1.domain.product.service;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.category.domain.Category;
import com.sparta.project1.domain.category.repository.CategoryRepository;
import com.sparta.project1.domain.category.service.CategoryFindService;
import com.sparta.project1.domain.product.api.dto.ProductRegisterRequest;
import com.sparta.project1.domain.product.api.dto.ProductResponse;
import com.sparta.project1.domain.product.domain.Product;
import com.sparta.project1.domain.product.domain.ProductOrderInfo;
import com.sparta.project1.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFindService {
    private final ProductRepository productRepository;
    private final CategoryFindService categoryFindService;

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("product not found"));

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice().setScale(0, RoundingMode.HALF_UP).longValue(),
                product.getDescription(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt()
        );
    }

    public PageResponse<ProductResponse> getListByKeyword(String name, Long minPrice, Long maxPrice, Long categoryId, int page, int size) {
        List<Long> categories = new ArrayList<>();
        if (categoryId != null) {
            categories = categoryFindService.findAllByParentId(categoryId);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Product> products = productRepository.findAllByKeywords(name, minPrice, maxPrice, categories, pageable);

        List<Product> productList = products.getContent();
        List<ProductResponse> contents = productList.stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getPrice().setScale(0, RoundingMode.HALF_UP).longValue(),
                        product.getDescription(),
                        product.getStock(),
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCreatedAt()
                ))
                .toList();

        return PageResponse.of(products, contents);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("product not found"));
    }

    public List<Product> getAllByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }
}

