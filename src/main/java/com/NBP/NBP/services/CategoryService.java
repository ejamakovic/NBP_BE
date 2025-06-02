package com.NBP.NBP.services;

import com.NBP.NBP.models.Category;
import com.NBP.NBP.models.dtos.PaginatedCategoryResponseDTO;
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

    public PaginatedCategoryResponseDTO getPaginatedCategories(Integer page, Integer size, String sortKey, String sortDirection) {
        if (page == null || size == null) {
            List<Category> allCategories = categoryRepository.findPaginated(null, null, sortKey, sortDirection);
            return new PaginatedCategoryResponseDTO(allCategories, 1, allCategories.size(), 0);
        }

        int offset = page * size;
        List<Category> categories = categoryRepository.findPaginated(offset, size, sortKey, sortDirection);
        int totalItems = categoryRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PaginatedCategoryResponseDTO(categories, totalPages, totalItems, page);
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