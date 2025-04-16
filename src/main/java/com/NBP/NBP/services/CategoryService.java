package com.NBP.NBP.services;

import com.NBP.NBP.models.Category;
import com.NBP.NBP.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    public int saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public int updateCategory(Category category) {
        return categoryRepository.update(category);
    }

    public int deleteCategory(int id) {
        return categoryRepository.delete(id);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}