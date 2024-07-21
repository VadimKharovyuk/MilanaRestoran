package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Payment;
import com.example.milanarestoran.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Метод для поиска платежа по идентификатору заказа
    public Payment findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    }
