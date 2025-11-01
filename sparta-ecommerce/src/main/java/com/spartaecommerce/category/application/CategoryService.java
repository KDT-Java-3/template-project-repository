package com.spartaecommerce.category.application;

import com.spartaecommerce.category.domain.commnad.CategoryRegisterCommand;
import com.spartaecommerce.category.domain.entity.Category;
import com.spartaecommerce.category.domain.repository.CategoryRepository;
import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long register(CategoryRegisterCommand registerCommand) {
        if (categoryRepository.existsByName(registerCommand.name())) {
            throw new BusinessException(ErrorCode.ENTITY_ALREADY_EXISTS, "Category name: " + registerCommand.name());
        }

        Category category = Category.createNew(registerCommand);

        return categoryRepository.save(category);
    }
}
