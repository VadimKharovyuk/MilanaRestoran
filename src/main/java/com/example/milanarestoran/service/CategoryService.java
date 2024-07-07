package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Booking;
import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public Category  saveCategory(Category category){
       return categoryRepository.save(category);
    }



    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
       return categoryRepository.findAll();

    }
    @Cacheable(value = "getCategoryById", key = "#id")
    public Category getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id).orElseThrow();
        } catch (DataAccessException e) {
          return categoryRepository.findById(id).orElseThrow();
        }

    }
}
