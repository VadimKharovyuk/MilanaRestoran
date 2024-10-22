package com.example.milanarestoran.model;//package com.example.milanarestoran.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "client_order")
public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//
//    private BigDecimal totalAmount;
//
//    private String deliveryAddress;
//
//    private LocalDateTime orderDate;
//
//    private String email;
//
//    private String phoneNumber;
//
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> orderItems = new ArrayList<>();
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private String email;
    private String phoneNumber;
    private Long cardId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


}
