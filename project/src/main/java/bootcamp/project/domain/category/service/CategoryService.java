package bootcamp.project.domain.category.service;

import bootcamp.project.domain.category.dto.CreateCategoryDto;
import bootcamp.project.domain.category.dto.UpdateCategoryDto;
import bootcamp.project.domain.category.entity.Category;
import bootcamp.project.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryDto createCategoryDto) {
        categoryRepository.save(
                Category.builder()
                        .name(createCategoryDto.getName())
                        .parent(categoryRepository.findById(createCategoryDto.getParent_id()).orElse(null))
                        .build()
        );
    }

    public void updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = categoryRepository.findById(updateCategoryDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Category parent = null;
        if (updateCategoryDto.getParentId() != null) {
            parent = categoryRepository.findById(updateCategoryDto.getParentId())
                    .orElse(null);

            if (parent != null && category.getId().equals(parent.getId())) {
                throw new IllegalArgumentException("자기 자신을 부모 카테고리로 설정할 수 없습니다.");
            }
        }
        category.update(parent, updateCategoryDto);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
