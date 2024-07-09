package com.example.milanarestoran.controller;//package com.example.milanarestoran.controller;
import com.example.milanarestoran.config.RabbitMQConfig;
import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.pojo.OrderMessage;
import com.example.milanarestoran.service.CartService;
import com.example.milanarestoran.service.EmailService;
import com.example.milanarestoran.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final EmailService emailService;


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
        // Обработка оформления заказа через сервис корзины
        cartService.checkoutCart(cart, deliveryAddress, email);

        // Формирование сообщения для клиента
        StringBuilder messageText = new StringBuilder();
        messageText.append("Спасибо за ваш заказ! Мы рады видеть вас в нашем кафе.\n\n");
        messageText.append("Ваш заказ:\n");

        cart.getDishes().forEach(dish -> {
            messageText.append(dish.getName())
                    .append(" - ")
                    .append("1 шт. - ")
                    .append(dish.getPrice())
                    .append(" руб.\n");
        });

        messageText.append("\nАдрес доставки: ").append(deliveryAddress);

        // Отправка письма с подтверждением заказа
        emailService.sendOrderMessage(email, messageText.toString());

        session.setAttribute("cart", null); // Очистка корзины после оформления заказа

        // Перенаправление на страницу подтверждения заказа
        return "redirect:/order/orderConfirmation";
    }
}
