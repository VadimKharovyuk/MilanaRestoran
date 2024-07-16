package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Cart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

public void sendOrderMessage(String recipientEmail, String messageText, ByteArrayInputStream pdfStream) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(recipientEmail);
    helper.setSubject("Подтверждение заказа в нашем кафе");
    helper.setText(messageText);

    helper.addAttachment("order.pdf", new ByteArrayResource(pdfStream.readAllBytes()));

    emailSender.send(message);
}
}