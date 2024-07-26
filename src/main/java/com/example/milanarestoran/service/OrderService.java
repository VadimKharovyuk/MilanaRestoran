package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service

public class OrderService {

    private final OrderRepository orderRepository;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();

    }

    private final RestTemplate restTemplate;
    private final String orderApiUrl = "http://localhost:4040/api/orders";

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
        // Логика создания заказа
    }


}
