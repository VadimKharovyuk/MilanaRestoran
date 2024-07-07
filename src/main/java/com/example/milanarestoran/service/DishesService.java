package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishesService {
    private final DishRepository dishRepository;


    public List<Dish> getAllDishes() {
       return dishRepository.findAll();

    }
    @Cacheable(value = "getDishById", key = "#id")
    public Dish getDishById(Long id) {
        try {
            return dishRepository.findById(id).orElseThrow();
        } catch (DataAccessException e) {
            return dishRepository.findById(id).orElseThrow();
        }
    }

    public Dish saveDish(Dish dish) {
       return dishRepository.save(dish);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    public List<Dish> getDishesByCategoryId(Long id) {
        return dishRepository.findByCategoryId(id);
    }
}
