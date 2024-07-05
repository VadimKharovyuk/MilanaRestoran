package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CartService {
    private final DishRepository dishRepository;

    public void addDishToCart(Cart cart, Dish dish) {
        cart.setTotalAmount(cart.getTotalAmount().add(BigDecimal.valueOf(dish.getPrice())));
        cart.getDishes().add(dish);
    }

    public void removeDishFromCart(Cart cart, Dish dish) {
        cart.setTotalAmount(cart.getTotalAmount().subtract(BigDecimal.valueOf(dish.getPrice())));
        cart.getDishes().remove(dish);
    }


    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
    }


}