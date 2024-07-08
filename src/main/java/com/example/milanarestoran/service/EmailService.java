package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Cart;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendOrderMessage(String recipientEmail, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение заказа в нашем кафе");
        message.setText(messageText);

        emailSender.send(message);
    }

//    public void sendOrderConfirmationEmail(String recipientEmail, Cart cart, String deliveryAddress) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(recipientEmail);
//        message.setSubject("Подтверждение заказа в нашем кафе");
//
//        // Формирование содержимого письма
//        StringBuilder emailContent = new StringBuilder();
//        emailContent.append("Спасибо за ваш заказ! Мы рады видеть вас в нашем кафе.\n\n");
//        emailContent.append("Ваш заказ:\n");
//
//        cart.getDishes().forEach(dish -> {
//            emailContent.append(dish.getName())
//                    .append(" - ")
//                    .append("1 шт. - ")  // Если количество не указано в Dish, можно указать по умолчанию 1
//                    .append(dish.getPrice())
//                    .append(" руб.\n");
//        });
//
//        emailContent.append("\nАдрес доставки: ").append(deliveryAddress);
//
//        message.setText(emailContent.toString());
//
//        emailSender.send(message);
//    }
}
