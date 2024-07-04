package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CartRepository extends JpaRepository<Cart,Long> {
}
