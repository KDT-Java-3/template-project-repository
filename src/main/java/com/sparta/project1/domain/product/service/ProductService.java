package com.sparta.project1.domain.product.service;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.category.domain.Category;
import com.sparta.project1.domain.category.repository.CategoryRepository;
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
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void register(ProductRegisterRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NoSuchElementException("category not found"));

        Product product = Product.register(request.name(), request.price(), request.description(), request.stock(), category);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getListByKeyword(String name, Long minPrice, Long maxPrice, Long categoryId, int page, int size) {
        List<Category> category = null;
        if (categoryId != null) {
            category = categoryRepository.findAllByParentId(categoryId);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Product> products = productRepository.findAllByKeywords(name, minPrice, maxPrice, category, pageable);

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

    public void updateProduct(Long id, ProductRegisterRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("product not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NoSuchElementException("category not found"));

        product.updateProduct(
                request.name(),
                request.price(),
                request.description(),
                request.stock(),
                category
        );

        productRepository.save(product);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void minusProductStock(List<ProductOrderInfo> productOrderInfos) {
        List<Product> products = productOrderInfos.stream()
                .map(p -> {
                    Product product = p.product();
                    product.minusStock(p.quantity());
                    return product;
                })
                .toList();
        productRepository.saveAll(products);
    }
}
