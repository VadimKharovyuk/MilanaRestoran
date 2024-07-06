package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.repository.OrderRepository;
import com.example.milanarestoran.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final OrderRepository orderRepository;

@GetMapping("/orderConfirmation")
public String showOrderConfirmationPage() {


    return "orderConfirmation";
}


    @GetMapping("/checkout")
    public String showCheckoutForm(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getDishes().isEmpty()) {
            // Redirect to some error page or handle empty cart scenario
            return "redirect:/";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("deliveryAddress", ""); // Initialize with empty delivery address
        return "order/checkout";
    }

//    @PostMapping("/checkout")
//    public String processOrder(HttpSession session, @RequestParam("deliveryAddress") String deliveryAddress, @RequestParam("email") String email) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null || cart.getDishes().isEmpty()) {
//            // Redirect to some error page or handle empty cart scenario
//            return "redirect:/";
//        }
//
//        // Assuming you have logged in user details accessible in session or through security context
//        // String userEmail = "example@example.com"; // Replace with actual logged in user email
//
//        cartService.checkoutCart(cart, deliveryAddress, email);
//        session.setAttribute("cart", null); // Clear cart after checkout
//
//        // Redirect to the order confirmation page
//        return "redirect:/orderConfirmation";
//    }



}
