package com.example.week01_project.service;

import com.example.week01_project.domain.category.Category;
import com.example.week01_project.dto.category.CategoryDtos.*;
import com.example.week01_project.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepo;

    @Transactional
    public Resp create(CreateReq req) {
        Category c = Category.builder()
                .name(req.name())
                .description(req.description())
                .parentId(req.parentId())
                .build();
        categoryRepo.save(c);
        return toResp(c);
    }

    @Transactional(readOnly = true)
    public List<Category> list() {
        return categoryRepo.findAll();
    }

    @Transactional
    public Resp update(Long id, UpdateReq req) {
        Category c = categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found"));
        c.setName(req.name());
        c.setDescription(req.description());
        c.setParentId(req.parentId());
        return toResp(c);
    }

    private Resp toResp(Category c) {
        return new Resp(c.getId(), c.getName(), c.getDescription(), c.getParentId());
    }
}

