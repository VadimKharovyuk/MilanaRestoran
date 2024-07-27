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

    public String makePayment( PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(paymentServiceUrl, requestEntity, String.class);
        return response.getBody();
    }
}
