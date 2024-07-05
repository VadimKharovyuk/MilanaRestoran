package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.service.CartService;
import com.example.milanarestoran.service.DishesService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final DishesService dishService;

    @GetMapping
    public String showCart(Model model,  HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    @PostMapping("/add/{dishId}")
    public String addDishToCart(@PathVariable Long dishId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        Dish dish = cartService.getDishById(dishId);
        cartService.addDishToCart(cart, dish);
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @PostMapping("/remove/{dishId}")
    public String removeDishFromCart(@PathVariable Long dishId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            Dish dish = dishService.getDishById(dishId);
            cartService.removeDishFromCart(cart, dish);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }
}