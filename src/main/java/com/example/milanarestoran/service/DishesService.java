package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishesService {
    private final DishRepository dishRepository;




    public Dish saveCategory(Dish dish){
        return dishRepository.save(dish);
    }


    public Dish findById(Long id){
        return dishRepository.findById(id).orElseThrow();
    }
    List<Dish> findAllBooking(){
        return dishRepository.findAll();
    }
    public void deleteById(Long id){
        dishRepository.deleteById(id);
    }
}
