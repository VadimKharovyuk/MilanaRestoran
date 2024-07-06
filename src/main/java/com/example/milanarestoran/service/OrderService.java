package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;



    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

//    public Order updateOrder(Long orderId, Order orderDetails) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
//
//        order.setUser(orderDetails.getUser());
//        order.setDate(orderDetails.getDate());
//        order.setTotalAmount(orderDetails.getTotalAmount());
//
//        return orderRepository.save(order);
//    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
