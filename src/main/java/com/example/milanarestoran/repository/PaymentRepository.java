package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
