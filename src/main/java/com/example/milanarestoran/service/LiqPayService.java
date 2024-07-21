package com.example.milanarestoran.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class LiqPayService {

    private final String PUBLIC_KEY = "PUBLIC_KEY";
    private final String PRIVATE_KEY = "PRIVATE_KEY";
    private final String API_URL = "https://www.liqpay.ua/api/3/checkout";

    public String createPayment(String amount, String currency, String description, String orderId) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(API_URL);

            String data = "{"
                    + "\"version\": \"3\","
                    + "\"public_key\": \"" + PUBLIC_KEY + "\","
                    + "\"amount\": \"" + amount + "\","
                    + "\"currency\": \"" + currency + "\","
                    + "\"description\": \"" + description + "\","
                    + "\"order_id\": \"" + orderId + "\","
                    + "\"result_url\": \"http://example.com/success\""
                    + "}";

            String signature = generateSignature(data, PRIVATE_KEY);

            post.setEntity(new StringEntity("data=" + data + "&signature=" + signature));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpResponse response = client.execute(post);
            return new ObjectMapper().readTree(response.getEntity().getContent()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private String generateSignature(String data, String privateKey) {
        try {
            String toSign = data + privateKey;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(toSign.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
