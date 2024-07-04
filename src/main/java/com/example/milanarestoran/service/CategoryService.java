package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Booking;
import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.repository.CategoryRepository;
import lombok.AllArgsConstructor;
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


    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow();
    }
    List<Category> findAllBooking(){
        return categoryRepository.findAll();
    }
    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }
}
