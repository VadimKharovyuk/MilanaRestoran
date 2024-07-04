package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DishRepository extends JpaRepository<Dish,Long> {
    List<Dish> findByCategoryId(Long id);

}
