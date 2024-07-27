package com.example.milanarestoran.OrderApiController;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Setter
@Getter
public class PaymentRequest {
    private String cardNumber;
    private double amount;
    private Date expirationDate;
    private String cvv;
}
