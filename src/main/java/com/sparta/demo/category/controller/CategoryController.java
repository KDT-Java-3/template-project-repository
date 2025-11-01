package com.sparta.demo.category.controller;

import com.sparta.demo.category.controller.request.CategorySaveRequest;
import com.sparta.demo.category.controller.request.CategoryUpdateRequest;
import com.sparta.demo.category.controller.response.CategoryFindAllResponse;
import com.sparta.demo.category.domain.Category;
import com.sparta.demo.category.service.CategoryService;
import com.sparta.demo.category.service.command.CategorySaveCommand;
import com.sparta.demo.category.service.command.CategoryUpdateCommand;
import com.sparta.demo.product.domain.Product;
import com.sparta.demo.product.domain.ProductRepository;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toMap;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    @PostMapping("/categories")
    ResponseEntity<Void> save(@RequestBody CategorySaveRequest request) {
        CategorySaveCommand categorySaveCommand = request.toCommand();
        Long id = categoryService.save(categorySaveCommand);
        return ResponseEntity.created(URI.create("/categories/" + id)).build();
    }

    @GetMapping("/categories")
    ResponseEntity<List<CategoryFindAllResponse>> findAll() {
        List<Category> categories = categoryService.findAll();

        Map<Category, List<Product>> categoryProductsMap = categories.stream()
                .collect(toMap(
                        Function.identity(),
                        category -> productRepository.findByCategoryId(category.getId())
                ));

        var responses = categoryProductsMap.entrySet().stream()
                .map(entry -> CategoryFindAllResponse.of(entry.getKey(), entry.getValue()))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/categories/{categoryId}")
    ResponseEntity<Void> update(
            @PathVariable Long categoryId,
            @RequestBody CategoryUpdateRequest request
    ) {
        CategoryUpdateCommand command = request.toCommand(categoryId);
        categoryService.update(command);
        return ResponseEntity.ok().build();
    }
}
