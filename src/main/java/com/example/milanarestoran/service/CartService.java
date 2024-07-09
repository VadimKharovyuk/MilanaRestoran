
package com.example.milanarestoran.service;

import com.example.milanarestoran.config.RabbitMQConfig;
import com.example.milanarestoran.model.*;
import com.example.milanarestoran.pojo.OrderMessage;
import com.example.milanarestoran.repository.DiscountRepository;
import com.example.milanarestoran.repository.DishRepository;
import com.example.milanarestoran.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final DiscountRepository discountRepository;


    //скидка в % соотношении

//    public void addDishToCart(Cart cart, Dish dish) {
//        BigDecimal dishPrice = BigDecimal.valueOf(dish.getPrice());
//        Optional<Discount> optionalDiscount = discountRepository.findActiveDiscountForCategory(dish.getCategory().getId(), LocalDate.now());
//
//        if (optionalDiscount.isPresent()) {
//            Discount discount = optionalDiscount.get();
//            BigDecimal discountAmount = dishPrice.multiply(discount.getAmount().divide(BigDecimal.valueOf(100)));
//            dishPrice = dishPrice.subtract(discountAmount);
//        }
//
//        cart.setTotalAmount(cart.getTotalAmount().add(dishPrice));
//        cart.getDishes().add(dish);
//    }
    //фиксированая скидка
public void addDishToCart(Cart cart, Dish dish) {
    BigDecimal dishPrice = BigDecimal.valueOf(dish.getPrice());
    Optional<Discount> optionalDiscount = discountRepository.findActiveDiscountForCategory(dish.getCategory().getId(), LocalDate.now());

    if (optionalDiscount.isPresent()) {
        Discount discount = optionalDiscount.get();
        // Вычитаем фиксированную скидку из цены
        dishPrice = dishPrice.subtract(discount.getAmount());
        // Убедитесь, что цена не станет отрицательной
        if (dishPrice.compareTo(BigDecimal.ZERO) < 0) {
            dishPrice = BigDecimal.ZERO;
        }
    }

    cart.setTotalAmount(cart.getTotalAmount().add(dishPrice));
    cart.getDishes().add(dish);
    logger.debug("Added dish to cart: {}, new total: {}", dish, cart.getTotalAmount());
}



    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public void clearCart(Cart cart) {
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.getDishes().clear();
    }


    @Transactional
    public void checkoutCart(Cart cart, String deliveryAddress, String userEmail) {
        Order order = new Order();
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setEmail(userEmail);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Dish dish : cart.getDishes()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setDish(dish);
            orderItem.setQuantity(1);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(cart.getTotalAmount());


        orderRepository.save(order);

        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setTotalAmount(order.getTotalAmount());
        orderMessage.setDeliveryAddress(order.getDeliveryAddress());
        orderMessage.setEmail(order.getEmail());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "", orderMessage);

        clearCart(cart);

        logger.debug("Saved order to database: {}", order);

    }
}





