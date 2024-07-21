package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findByOrderId(Long orderId);

}
