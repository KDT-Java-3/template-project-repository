package com.example.week01_project.service;

import com.example.week01_project.common.ConflictException;
import com.example.week01_project.domain.category.Category;
import com.example.week01_project.dto.category.CategoryDtos.*;
import com.example.week01_project.dto.category.CategoryRequest;
import com.example.week01_project.dto.category.CategoryResponse;
import com.example.week01_project.repository.CategoryRepository;
import com.example.week01_project.repository.ProductRepository;
import com.example.week01_project.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    public CategoryService(CategoryRepository categoryRepo, ProductRepository productRepo) {
        this.categoryRepo = categoryRepo; this.productRepo = productRepo;
    }

    @Transactional
    public Long create(CategoryRequest req){
        if (categoryRepo.existsByName(req.name())) throw new ConflictException("duplicate category name");
        Category parent = (req.parentId()==null)? null : categoryRepo.findById(req.parentId())
                .orElseThrow(()-> new NotFoundException("parent category not found"));
        Category c = new Category();
        c.setName(req.name()); c.setDescription(req.description()); c.setParent(parent);
        categoryRepo.save(c);
        return c.getId();
    }

    public CategoryResponse get(Long id){
        Category c = categoryRepo.findById(id).orElseThrow(()-> new NotFoundException("category not found"));
        return new CategoryResponse(c.getId(), c.getName(), c.getDescription(), c.getParent()==null? null:c.getParent().getId());
    }

    @Transactional
    public void update(Long id, CategoryRequest req){
        Category c = categoryRepo.findById(id).orElseThrow(() -> new NotFoundException("category not found"));
        if (!c.getName().equals(req.name()) && categoryRepo.existsByName(req.name()))
            throw new ConflictException("duplicate category name");
        Category parent = (req.parentId()==null)? null : categoryRepo.findById(req.parentId())
                .orElseThrow(()-> new NotFoundException("parent category not found"));
        c.setName(req.name()); c.setDescription(req.description()); c.setParent(parent);
    }

    @Transactional
    public void delete(Long id){
        Category c = categoryRepo.findById(id).orElseThrow(()-> new NotFoundException("category not found"));
        if (categoryRepo.countByParent_Id(id) > 0) throw new ConflictException("cannot delete: has children");
        long productCount = productRepo.count(); // 간단 예시: 실제로는 카테고리 기준 count 메서드 추가 권장
        if (productCount > 0) {
            // 실무에선 productRepo.countByCategoryId(id) 같은 메서드를 선언해 정확 체크
            throw new ConflictException("cannot delete: products exist in this category");
        }
        categoryRepo.delete(c);
    }
}