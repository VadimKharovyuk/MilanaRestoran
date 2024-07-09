package com.example.milanarestoran.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToMany
    @JoinTable(
            name = "cart_dishes",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes = new ArrayList<>();

}
