package com.example.milanarestoran.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class OrderMessage  implements Serializable {
    private Long id;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private String email;
}
