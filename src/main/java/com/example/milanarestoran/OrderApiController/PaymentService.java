package com.example.milanarestoran.OrderApiController;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl = "http://localhost:8091/api/payments";

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makePayment(String cardNumber, double amount) {
        String url = paymentServiceUrl + "?cardNumber=" + cardNumber + "&amount=" + amount;

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        return response.getBody();
    }
}
