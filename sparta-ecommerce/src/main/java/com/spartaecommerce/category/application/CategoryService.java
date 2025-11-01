package com.spartaecommerce.category.application;

import com.spartaecommerce.category.domain.commnad.CategoryRegisterCommand;
import com.spartaecommerce.category.domain.entity.Category;
import com.spartaecommerce.category.domain.query.CategorySearchQuery;
import com.spartaecommerce.category.domain.repository.CategoryRepository;
import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long register(CategoryRegisterCommand registerCommand) {
        if (categoryRepository.existsByName(registerCommand.name())) {
            throw new BusinessException(ErrorCode.ENTITY_ALREADY_EXISTS, "Category name: " + registerCommand.name());
        }

        Category category = Category.createNew(registerCommand);

        return categoryRepository.save(category);
    }

    public List<Category> search(CategorySearchQuery searchQuery) {
        Product product = productRepository.getById(searchQuery.productId());
        Category category = categoryRepository.getById(product.getCategoryId());
        return List.of(category);
    }
}
