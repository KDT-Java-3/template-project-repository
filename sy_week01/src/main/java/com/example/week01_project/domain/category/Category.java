package com.example.week01_project.domain.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    // getter/setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getParent() { return parent; }
    public List<Category> getChildren() { return children; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setParent(Category parent) { this.parent = parent; }
}