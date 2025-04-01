package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Category;
import com.NBP.NBP.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        int result = categoryService.saveCategory(category);
        return result > 0 ? ResponseEntity.ok("Category created successfully") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category category) {
        category.setId(id);
        int result = categoryService.updateCategory(category);
        return result > 0 ? ResponseEntity.ok("Category updated successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        int result = categoryService.deleteCategory(id);
        return result > 0 ? ResponseEntity.ok("Category deleted successfully") : ResponseEntity.badRequest().build();
    }
}