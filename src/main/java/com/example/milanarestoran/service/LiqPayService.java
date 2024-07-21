package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.model.Order;
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


    public String createPayment(Dish dish, String currency, String description, Order order) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(API_URL);

            // Преобразуем цену из Double в String и создаем JSON данные
            String amount = new BigDecimal(dish.getPrice()).setScale(2, RoundingMode.HALF_UP).toString();
            String data = createJsonData(amount, currency, description, order);
            String signature = generateSignature(data, PRIVATE_KEY);

            post.setEntity(new StringEntity("data=" + data + "&signature=" + signature));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpResponse response = client.execute(post);
            return new ObjectMapper().readTree(response.getEntity().getContent()).toString();
        } catch (Exception e) {
            // Логирование и обработка ошибок
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


    private String createJsonData(String amount, String currency, String description, Order order) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("version", "3");
        node.put("public_key", PUBLIC_KEY);
        node.put("amount", amount);
        node.put("currency", currency);
        node.put("description", description);
        node.put("order_id", order.getId().toString()); // Используйте идентификатор заказа из объекта Order
        node.put("result_url", "http://example.com/success"); // Замените на ваш URL

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
