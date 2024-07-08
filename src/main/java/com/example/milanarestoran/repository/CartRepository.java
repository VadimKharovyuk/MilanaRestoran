package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Cart;
import com.example.milanarestoran.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CartRepository extends JpaRepository<Cart,Long> {

}
