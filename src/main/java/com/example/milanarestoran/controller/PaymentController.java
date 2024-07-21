package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.service.DishesService;
import com.example.milanarestoran.service.LiqPayService;
import com.example.milanarestoran.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {

    private final LiqPayService liqPayService;
    private final DishesService dishesService;
    private final OrderService orderService;



    @PostMapping("/create")
    public String createPayment(@RequestParam Long dishId, @RequestParam Long orderId,
                                @RequestParam String currency, @RequestParam String description) {
        // Замените это на ваш способ получения объекта Dish и Order, например из базы данных
        Dish dish = getDishById(dishId); // Реализуйте метод для получения объекта Dish по ID
        Order order = getOrderById(orderId); // Реализуйте метод для получения объекта Order по ID

        if (dish == null || order == null) {
            return "Error: Dish or Order not found";
        }

        return liqPayService.createPayment(dish, currency, description, order);
    }

    private Dish getDishById(Long dishId) {
        dishesService.getDishById(dishId);
        // Реализуйте метод для получения объекта Dish из базы данных
        // Например, используйте ваш репозиторий для получения данных
        return null; // Замените на реальную реализацию
    }

    private Order getOrderById(Long orderId) {
        orderService.getOrderById(orderId);
        return null; // Замените на реальную реализацию
    }
}
