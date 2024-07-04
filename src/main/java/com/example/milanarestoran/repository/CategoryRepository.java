package com.example.milanarestoran.repository;

import com.example.milanarestoran.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
