package com.example.milanarestoran.model;//package com.example.milanarestoran.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "client_order")
//public class Order {
//   @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "client_id")
//    private User user;
//
//    private LocalDate date;
//    private BigDecimal totalAmount;
//}


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
@Table(name = "client_order")
public class Order {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

// @ManyToOne
// @JoinColumn(name = "user_id")
// private User user;

 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
 private List<OrderItem> orderItems = new ArrayList<>();

 private BigDecimal totalAmount;

 private String deliveryAddress;

 private LocalDateTime orderDate;

 private String email;



}
