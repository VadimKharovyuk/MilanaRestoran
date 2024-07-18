package com.example.milanarestoran.controller;//package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.repository.DishRepository;
import com.example.milanarestoran.service.CartService;
import com.example.milanarestoran.service.EmailService;
import com.example.milanarestoran.service.PdfService;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    private final HttpSession httpSession;
    private final EmailService emailService;
    private final DishRepository dishRepository;
    private final PdfService pdfService;


//    @GetMapping
//    public String showCart(Model model) {
//        Cart cart = (Cart) httpSession.getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            httpSession.setAttribute("cart", cart);
//        }
//        model.addAttribute("cart", cart);
//        return "cart/view";
//    }

    //    @PostMapping("/add/{dishId}")
//    public String addDishToCart(@PathVariable Long dishId) {
//        Cart cart = (Cart) httpSession.getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            httpSession.setAttribute("cart", cart);
//        }
//
//        Dish dish = cartService.getDishById(dishId);
//        cartService.addDishToCart(cart, dish);
//        httpSession.setAttribute("cart", cart);
//
//        return "redirect:/dishes";
//    }
    @GetMapping()
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
    public String addToCart(@PathVariable("dishId") Long dishId) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }

        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new RuntimeException("Dish not found"));
        cartService.addDishToCart(cart, dish);

        return "redirect:/dishes";
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
public String processOrder(HttpSession session, @RequestParam("deliveryAddress") String deliveryAddress, @RequestParam("email") String email , @RequestParam String  phoneNumber) {
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.getDishes().isEmpty()) {
        return "redirect:/";
    }
    // Process the order using the cart service
    Order order = cartService.checkoutCart(cart, deliveryAddress, email,phoneNumber);

    // Generate the PDF
    ByteArrayInputStream pdfStream;
    try {
        pdfStream = pdfService.generateOrderPdf(order);
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
        return "redirect:/order/checkout?error";
    }

    // Prepare the email message
    StringBuilder messageText = new StringBuilder();
    messageText.append("Дорогой клиент,\n\n");
    messageText.append("Спасибо за ваш заказ! Мы рады приветствовать вас в нашем кафе.\n");
    messageText.append("Мы ценим ваш выбор и обещаем вкусную и быструю доставку.\n\n");
    messageText.append("Ваш заказ:\n");

    cart.getDishes().forEach(dish -> {
        messageText.append(dish.getName())
                .append(" - ")
                .append("1 шт. - ")
                .append(dish.getPrice())
                .append(" руб.\n");
    });

    messageText.append("\nАдрес доставки: ").append(deliveryAddress).append("\n\n");
    messageText.append("С наилучшими пожеланиями,\n");
    messageText.append("Команда вашего любимого кафе");

    // Send the email with the PDF attachment
    try {
        emailService.sendOrderMessage(email, messageText.toString(), pdfStream);
    } catch (MessagingException e) {
        e.printStackTrace();
        return "redirect:/order/checkout?error";
    }

    session.setAttribute("cart", null); // Clear the cart after order processing

    // Redirect to the order confirmation page
    return "redirect:/order/orderConfirmation";
}


}
