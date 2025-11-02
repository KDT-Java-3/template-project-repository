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
        if  (request.getCategoryId() == null || categoryRepository.existsById(request.getCategoryId())) {
            // 원래는 예외를 던져야 함.
            return null;
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if  (request.getUserId() == null || userRepository.existsById(request.getUserId())) {
            // 원래는 예외를 던져야 함.
            return null;
        }

        User user = userRepository.findById(request.getUserId()).orElse(null);

        Product product = Product.builder()
            .name(request.getName())
            .price(request.getPrice())
            .stock(request.getStock())
            .category(category)
            .user(user)
            .build();

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
        if (request.getCategoryId() != null || categoryRepository.existsById(request.getCategoryId())) {
            return null;
        }

        if (request.getPriseFrom() > request.getPriseTo()) {
            return null;
        }

        // 일단 단일 조건으로 구현.
        if (request.getCategoryId() != null && categoryRepository.existsById(request.getCategoryId())) {
            return productRepository.findAllByCategory_Id(request.getCategoryId()).stream().map(
                product -> new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
                )
            ).toList();
        }

        if (request.getPriseTo() >= 0 || request.getPriseFrom() >= 0) {
            return productRepository.findAllByPriceBetween(
                request.getPriseFrom(), request.getPriseTo()
            ).stream().map(
                product -> new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
                )
            ).toList();
        }

        if (request.getQ() != null) {
            List<Product> findByName = productRepository.findAllByNameContains(request.getQ());
            List<Product> findByDescription = productRepository.findAllByDescriptionContains(request.getQ());
            Set<Product> all = new HashSet<>();
            all.addAll(findByName);
            all.addAll(findByDescription);

            return all.stream()
                .map(product -> new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
                ))
                .toList();
        }

        // 만일 조건이 없는경우 전체 출력
        return productRepository.findAll().stream().map(
            product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
            )
        ).toList();
    }

    @Override
    public ProductResponse get(Long id) {
        if  (id == null) {
            return null;
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock()
        );
    }

    @Override
    @Transactional
    public Boolean update(Long id, ProductUpdateRequest request) {
        if (request.getCategoryId() != null && !categoryRepository.existsById(request.getCategoryId())) {
            return false;
        }

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }

        Category category = product.getCategory();
        User user = product.getUser();

        if (request.getCategoryId() != null && categoryRepository.existsById(request.getCategoryId())) {
            category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        }

        product.update(request, category, user);
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long id, Long userId) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user != null || !product.getUser().getId().equals(userId)) {
            return false;
        }
        // CASCADE 필요
        product.getCategory().removeProduct(product);
        product.getUser().removeProduct(product);
        productRepository.delete(product);
        return true;
    }


}
