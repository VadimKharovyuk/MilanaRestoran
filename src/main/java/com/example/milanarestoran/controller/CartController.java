package com.example.milanarestoran.controller;//package com.example.milanarestoran.controller;
//
//import com.example.milanarestoran.model.Cart;
//import com.example.milanarestoran.model.Dish;
//import com.example.milanarestoran.service.CartService;
//import com.example.milanarestoran.service.DishesService;
//import jakarta.servlet.http.HttpSession;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//@Controller
//@RequestMapping("/cart")
//@AllArgsConstructor
//public class CartController {
//    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
//    private final CartService cartService;
//
//
//
//    @GetMapping
//    public String showCart(Model model,  HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
//        model.addAttribute("cart", cart);
//        return "cart/view";
//    }
//
//@PostMapping("/add/{dishId}")
//public String addDishToCart(@PathVariable Long dishId, HttpSession session) {
//    Cart cart = (Cart) session.getAttribute("cart");
//    if (cart == null) {
//        cart = new Cart();
//        session.setAttribute("cart", cart);
//    }
//
//    Dish dish = cartService.getDishById(dishId);
//    cartService.addDishToCart(cart, dish);
//    session.setAttribute("cart", cart);
//
//    return "redirect:/dishes";
//}
//@PostMapping("/remove/{dishId}")
//public String removeDishFromCart(@PathVariable Long dishId, HttpSession session) {
//    Cart cart = (Cart) session.getAttribute("cart");
//    if (cart != null) {
//        Dish dish = cartService.getDishById(dishId);
//        cartService.removeDishFromCart(cart, dish);
//        session.setAttribute("cart", cart);
//        logger.debug("Removed dish: {} from cart", dish);
//    }
//    return "redirect:/cart";
//}
//
//
//    @PostMapping("/clear")
//    public String clearCart(HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart != null) {
//            cartService.clearCart(cart);
//            session.setAttribute("cart", cart);
//            logger.debug("Cleared the cart");
//        }
//        return "redirect:/cart";
//    }
//
//}

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    private final HttpSession httpSession;

    @GetMapping
    public String showCart(Model model) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    @PostMapping("/add/{dishId}")
    public String addDishToCart(@PathVariable Long dishId) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }

        Dish dish = cartService.getDishById(dishId);
        cartService.addDishToCart(cart, dish);
        httpSession.setAttribute("cart", cart);

        return "redirect:/dishes";
    }

    @PostMapping("/remove/{dishId}")
    public String removeDishFromCart(@PathVariable Long dishId) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart != null) {
            Dish dish = cartService.getDishById(dishId);
            cartService.removeDishFromCart(cart, dish);
            httpSession.setAttribute("cart", cart);
            logger.debug("Removed dish: {} from cart", dish);
        }
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart != null) {
            cartService.clearCart(cart);
            httpSession.setAttribute("cart", cart);
            logger.debug("Cleared the cart");
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String processOrder(HttpSession session, @RequestParam("deliveryAddress") String deliveryAddress, @RequestParam("email") String email) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getDishes().isEmpty()) {

            return "redirect:/";
        }

        cartService.checkoutCart(cart, deliveryAddress, email);
        session.setAttribute("cart", null); // Clear cart after checkout

        // Redirect to the order confirmation page
        return "redirect:/order/orderConfirmation";
    }



}
