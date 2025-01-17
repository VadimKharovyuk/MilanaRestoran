package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orderConfirmation")
    public String showOrderConfirmationPage() {
        return "order/orderConfirmation";
    }

@GetMapping("/checkout")
public String showCheckoutForm(Model model, HttpSession session) {
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.getDishes().isEmpty()) {
        return "redirect:/";
    }

    model.addAttribute("cart", cart);
    model.addAttribute("deliveryAddress", ""); // Initialize with empty delivery address
    return "order/checkout";
}

    @GetMapping("/allOrders")
    public String showAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order";
    }


    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("id") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/order/allOrders";
    }


}