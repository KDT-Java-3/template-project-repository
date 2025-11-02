package bootcamp.project.domain.category.controller;

import bootcamp.project.domain.category.dto.CreateCategoryDto;
import bootcamp.project.domain.category.dto.UpdateCategoryDto;
import bootcamp.project.domain.category.entity.Category;
import bootcamp.project.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public String createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        categoryService.createCategory( createCategoryDto);
        return "success";
    }

    @PostMapping("/update")
    public String updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto) {
        categoryService.updateCategory( updateCategoryDto);
        return "success";
    }

    @GetMapping("/search")
    public List<Category> searchCategory(@RequestParam String categoryName) {
        return categoryService.findAllCategories();
    }
}
