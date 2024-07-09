package com.example.milanarestoran.service;

import com.example.milanarestoran.repository.DiscountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    public void delete(Long id){
        discountRepository.deleteById(id);
    }
}
