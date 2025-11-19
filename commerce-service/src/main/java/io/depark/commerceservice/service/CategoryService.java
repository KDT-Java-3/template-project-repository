package io.depark.commerceservice.service;

import io.depark.commerceservice.common.ServiceException;
import io.depark.commerceservice.common.ServiceExceptionCode;
import io.depark.commerceservice.controller.dto.CategoryRequest;
import io.depark.commerceservice.controller.dto.CategoryResponse;
import io.depark.commerceservice.entity.Category;
import io.depark.commerceservice.repository.CategoryJpaRepository;
import io.depark.commerceservice.repository.ProductJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        Category parent = null;
        if (!isEmpty(request.getParentId())) {
            parent = categoryJpaRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PARENT_CATEGORY));
        }

        return CategoryResponse.fromEntity(
                categoryJpaRepository.save(
                        Category.builder()
                                .name(request.getName())
                                .description(request.getDescription())
                                .parent(parent)
                                .build()
                )
        );
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));

        Category parent = null;
        if (!isEmpty(request.getParentId())) {
            parent = categoryJpaRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PARENT_CATEGORY));
        }

        category.updateDetails(request.getName(), request.getDescription(), parent);

        return CategoryResponse.fromEntity(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));

        if (categoryJpaRepository.existsByParentId(id)
                || productJpaRepository.existsByCategory(category)
        ) {
            throw new ServiceException(ServiceExceptionCode.NOT_ALLOWED_DELETE_CATEGORY);
        }

        categoryJpaRepository.delete(category);
    }
}
