package com.example.milanarestoran.service;

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
            return fetchDishFromCacheOrDatabase(id);
        } catch (DataAccessException e) {
            // Логируем ошибку доступа к базе данных или кэшу
            e.printStackTrace();
            // В случае ошибки, повторно пытаемся получить данные из базы данных
            return dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish not found with id: " + id));
        }
    }

    private Dish fetchDishFromCacheOrDatabase(Long id) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish not found with id: " + id));
        // Проверяем, если dish равен null, что может указывать на отсутствие данных в кэше
        if (dish == null) {
            throw new RuntimeException("Dish not found with id: " + id);
        }
        return dish;
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
