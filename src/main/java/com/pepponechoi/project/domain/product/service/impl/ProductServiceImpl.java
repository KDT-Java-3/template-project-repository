package com.pepponechoi.project.domain.product.service.impl;

import com.pepponechoi.project.domain.category.entity.Category;
import com.pepponechoi.project.domain.category.repository.CategoryRepository;
import com.pepponechoi.project.domain.product.dto.request.ProductCreateRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductReadRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductUpdateRequest;
import com.pepponechoi.project.domain.product.dto.response.ProductResponse;
import com.pepponechoi.project.domain.product.entity.Product;
import com.pepponechoi.project.domain.product.repository.ProductRepository;
import com.pepponechoi.project.domain.product.service.ProductService;
import com.pepponechoi.project.domain.user.entity.User;
import com.pepponechoi.project.domain.user.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return null;
        }

        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return null;
        }

        Product product = new Product(
            request.getName(),
            request.getPrice(),
            request.getStock(),
            category,
            user
        );

        productRepository.save(product);

        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock()
        );
    }

    @Override
    public List<ProductResponse> list(ProductReadRequest request) {
        if (request.getPriseFrom() != null && request.getPriseTo() != null
            && request.getPriseFrom() > request.getPriseTo()) {
            return null;
        }

        if (request.getCategoryId() != null) {
            return productRepository.findAllByCategory_Id(request.getCategoryId())
                .stream()
                .map(this::toProductResponse)
                .toList();
        }

        if (request.getPriseFrom() != null && request.getPriseTo() != null) {
            return productRepository.findAllByPriceBetween(
                    request.getPriseFrom(),
                    request.getPriseTo()
                )
                .stream()
                .map(this::toProductResponse)
                .toList();
        }

        if (request.getQ() != null && !request.getQ().isBlank()) {
            List<Product> findByName = productRepository.findAllByNameContains(request.getQ());
            List<Product> findByDescription = productRepository.findAllByDescriptionContains(request.getQ());

            Set<Product> all = new HashSet<>();
            all.addAll(findByName);
            all.addAll(findByDescription);

            return all.stream()
                .map(this::toProductResponse)
                .toList();
        }

        return productRepository.findAll()
            .stream()
            .map(this::toProductResponse)
            .toList();
    }

    @Override
    public ProductResponse get(Long id) {
        if (id == null) {
            return null;
        }

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        return toProductResponse(product);
    }

    @Override
    @Transactional
    public Boolean update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }

        Category category = product.getCategory();
        if (request.getCategoryId() != null) {
            Category newCategory = categoryRepository.findById(request.getCategoryId()).orElse(null);
            if (newCategory == null) {
                return false;
            }
            category = newCategory;
        }

        User user = product.getUser();
        product.update(request, category, user);

        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long id, Long userId) {
        // 1. Product 조회
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        if (!product.getUser().getId().equals(userId)) {
            return false;
        }

        product.getCategory().removeProduct(product);
        product.getUser().removeProduct(product);
        productRepository.delete(product);

        return true;
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock()
        );
    }
}