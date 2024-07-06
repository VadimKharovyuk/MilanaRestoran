//package com.example.milanarestoran.service;
//import com.example.milanarestoran.model.Cart;
//import com.example.milanarestoran.model.Dish;
//import com.example.milanarestoran.repository.CartRepository;
//import com.example.milanarestoran.repository.DishRepository;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//@AllArgsConstructor
//public class CartService {
//
//    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
//    private final DishRepository dishRepository;
//    private final CartRepository cartRepository;
//
//public void addDishToCart(Cart cart, Dish dish) {
//    cart.setTotalAmount(cart.getTotalAmount().add(BigDecimal.valueOf(dish.getPrice())));
//    cart.getDishes().add(dish);
//    logger.debug("Added dish to cart: {}, new total: {}", dish, cart.getTotalAmount());
//}
//
//    public void removeDishFromCart(Cart cart, Dish dish) {
//        cart.setTotalAmount(cart.getTotalAmount().subtract(BigDecimal.valueOf(dish.getPrice())));
//        cart.getDishes().remove(dish);
//    }
//
//    public Dish getDishById(Long dishId) {
//        return dishRepository.findById(dishId)
//                .orElseThrow(() -> new RuntimeException("Dish not found"));
//    }
//
//    public void clearCart(Cart cart) {
//        cart.setTotalAmount(BigDecimal.ZERO);
//        cart.getDishes().clear();
//    }
//
//
//    public void saveCartToDatabase(Cart cart) {
//    cartRepository.save(cart);
//
//    }
//}


package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.model.OrderItem;
import com.example.milanarestoran.repository.CartRepository;
import com.example.milanarestoran.repository.DishRepository;
import com.example.milanarestoran.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final DishRepository dishRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

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


    @Transactional
    public void checkoutCart(Cart cart, String deliveryAddress, String userEmail) {
        Order order = new Order();
//        order.setUser(cart.getUser()); // Assuming cart has a user associated with it
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setEmail(userEmail);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Dish dish : cart.getDishes()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setDish(dish);
            orderItem.setQuantity(1); // Assuming quantity is 1 per dish
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(cart.getTotalAmount());

        orderRepository.save(order);

        // Clear the cart after saving order
        clearCart(cart);

        logger.debug("Saved order to database: {}", order);
    }



}
