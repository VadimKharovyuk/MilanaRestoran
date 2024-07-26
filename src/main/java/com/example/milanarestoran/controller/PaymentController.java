package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Payment;
import com.example.milanarestoran.service.DishesService;
import com.example.milanarestoran.service.LiqPayService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
public class PaymentController {
    private final DishesService dishesService;


@GetMapping("/pay")
    public String pay(Model model ,HttpSession session ){
// Извлекаем корзину из сессии
    Cart cart = (Cart) session.getAttribute("cart");

    // Проверяем, есть ли корзина в сессии
    if (cart == null || cart.getDishes().isEmpty()) {
        // Если корзина пуста, перенаправляем на главную страницу
        return "redirect:/";
    }

    // Добавляем корзину и ее товары в модель
    model.addAttribute("cart", cart);
    model.addAttribute("deliveryAddress", ""); // Инициализируем пустое поле для адреса доставки


    return "payment/Payment";
}
}
