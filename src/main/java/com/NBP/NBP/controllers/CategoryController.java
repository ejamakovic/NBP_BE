package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Category;
import com.NBP.NBP.models.dtos.PaginatedCategoryResponseDTO;
import com.NBP.NBP.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    // @GetMapping
    // public List<Category> getAllCategories() {
    //     return categoryService.getAllCategories();
    // }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public ResponseEntity<PaginatedCategoryResponseDTO> getAllCategories(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortKey", defaultValue = "name") String sortKey,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

        PaginatedCategoryResponseDTO response = categoryService.getPaginatedCategories(page, size, sortKey, sortDirection);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        int result = categoryService.saveCategory(category);
        return result > 0 ? ResponseEntity.ok("Category created successfully") : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category category) {
        category.setId(id);
        int result = categoryService.updateCategory(category);
        return result > 0 ? ResponseEntity.ok("Category updated successfully") : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        int result = categoryService.deleteCategory(id);
        return result > 0 ? ResponseEntity.ok("Category deleted successfully") : ResponseEntity.badRequest().build();
    }
}
