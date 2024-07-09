package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    @Query("SELECT d FROM Discount d WHERE d.category.id = :categoryId AND d.startDate <= :date AND d.endDate >= :date")
    Optional<Discount> findActiveDiscountForCategory(@Param("categoryId") Long categoryId, @Param("date") LocalDate date);
}
