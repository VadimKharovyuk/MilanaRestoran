package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Dish;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class LiqPayService {

    @Value("${liqpay.public_key}")
    private String PUBLIC_KEY;

    @Value("${liqpay.private_key}")
    private String PRIVATE_KEY;

    @Value("${liqpay.api_url}")
    private String API_URL;

    public String createPayment(String cartId, BigDecimal totalAmount, String currency, String description) {
        try {
            String amount = totalAmount.setScale(2, RoundingMode.HALF_UP).toString();

            // Create JSON data
            String jsonData = createJsonData(amount, currency, cartId);

            // Generate signature
            String signature = generateSignature(jsonData, PRIVATE_KEY);

            // Create request body
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            node.put("data", jsonData);
            node.put("signature", signature);
            String requestBody = node.toString();

            // Send request
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(API_URL);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(requestBody));

            HttpResponse response = httpClient.execute(post);
            return response.getEntity().getContent().toString(); // Handle response as needed

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String createJsonData(String amount, String currency, String orderId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("version", "3");
        node.put("public_key", PUBLIC_KEY);
        node.put("amount", amount);
        node.put("currency", currency);
        node.put("order_id", orderId);
        node.put("result_url", "http://example.com/success"); // Replace with your URL

        return node.toString();
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
