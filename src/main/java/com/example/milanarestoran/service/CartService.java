package com.example.milanarestoran.service;
import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final DishRepository dishRepository;

public void addDishToCart(Cart cart, Dish dish) {
    cart.setTotalAmount(cart.getTotalAmount().add(BigDecimal.valueOf(dish.getPrice())));
    cart.getDishes().add(dish);
    logger.debug("Added dish to cart: {}, new total: {}", dish, cart.getTotalAmount());
}

    public void removeDishFromCart(Cart cart, Dish dish) {
        cart.setTotalAmount(cart.getTotalAmount().subtract(BigDecimal.valueOf(dish.getPrice())));
        cart.getDishes().remove(dish);
    }

    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public void clearCart(Cart cart) {
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.getDishes().clear();
    }




}