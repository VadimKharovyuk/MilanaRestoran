package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.model.Payment;
import com.example.milanarestoran.service.LiqPayService;
import com.example.milanarestoran.service.OrderService; // Добавьте сервис для работы с заказами
import com.example.milanarestoran.service.PaymentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    private final LiqPayService liqPayService;
    private final OrderService orderService; // Добавьте сервис для работы с заказами

    @GetMapping("/form")
    public String showPaymentForm(Model model) {
        model.addAttribute("paymentForm", new Payment());
        return "payment/payment-form";
    }

    @PostMapping("/process-payment")
    public String processPayment(@ModelAttribute Payment paymentForm, Model model) {
        try {
            Order order = orderService.findById(paymentForm.getOrder().getId()); // Получите заказ по ID
            if (order == null) {
                throw new IllegalArgumentException("Order not found");
            }

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(paymentForm.getAmount().toString());
            payment.setCurrency(paymentForm.getCurrency());
            payment.setStatus("Pending");
            payment.setDescription(paymentForm.getDescription());
            payment.setPaymentDate(java.time.LocalDate.now().toString());
            payment.setCardNumber(paymentForm.getCardNumber());
            payment.setCardHolderName(paymentForm.getCardHolderName());
            payment.setExpirationDate(paymentForm.getExpirationDate());
            payment.setCvv(paymentForm.getCvv());

            paymentService.save(payment);

            // Создание платежа через LiqPay
            String paymentResponse = liqPayService.createPayment(
                    paymentForm.getAmount(),
                    paymentForm.getCurrency(),
                    paymentForm.getDescription(),
                    paymentForm.getOrder().getId().toString()
            );

            // Обработка ответа LiqPay и перенаправление пользователя на страницу оплаты
            model.addAttribute("paymentResponse", paymentResponse);
            return "payment/payment-status";
        } catch (Exception e) {
            logger.error("An error occurred while processing the payment", e);
            model.addAttribute("statusMessage", "An error occurred while processing the payment.");
            return "payment/payment-status";
        }
    }

    @GetMapping("/payment-status")
    public String getPaymentStatus(@RequestParam("orderId") Long orderId, Model model) {
        // Логика получения статуса платежа
        String message = "Payment processing initiated for Order ID: " + orderId;
        model.addAttribute("statusMessage", message);
        return "payment/payment-status";
    }

    @PostMapping("/payment-result")
    public String handlePaymentResult(@RequestParam("order_id") Long orderId,
                                      @RequestParam("amount") BigDecimal amount,
                                      @RequestParam("status") String status, Model model) {
        try {
            Order order = orderService.findById(orderId); // Получите заказ по ID
            if (order == null) {
                throw new IllegalArgumentException("Order not found");
            }

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(amount.toString());
            payment.setStatus(status);
            payment.setCurrency("UAH");
            payment.setDescription("Test payment");
            payment.setPaymentDate(java.time.LocalDate.now().toString());

            paymentService.save(payment);

            String message = "success".equals(status)
                    ? "Payment was successful! Order ID: " + orderId
                    : "Payment failed for Order ID: " + orderId;
            model.addAttribute("statusMessage", message);
            return "payment/payment-status";
        } catch (Exception e) {
            logger.error("An error occurred while handling the payment result", e);
            model.addAttribute("statusMessage", "An error occurred while processing the payment result.");
            return "payment/payment-status";
        }
    }

    @PostMapping("/payment-notification")
    public void handlePaymentNotification(@RequestParam Map<String, String> params) {
        String orderIdStr = params.get("order_id");
        if (orderIdStr == null || params.get("status") == null) {
            return;
        }

        try {
            Long orderId = Long.valueOf(orderIdStr);
            String status = params.get("status");

            Payment payment = paymentService.findByOrderId(orderId);
            if (payment != null) {
                payment.setStatus(status);
                paymentService.save(payment);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid order ID format", e);
        } catch (Exception e) {
            logger.error("An error occurred while handling payment notification", e);
        }
    }
}
