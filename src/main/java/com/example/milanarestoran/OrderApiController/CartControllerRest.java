package com.example.milanarestoran.OrderApiController;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartControllerRest {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/pay")
    public String checkout(Model model) {
        // Передача суммы корзины в форму

        return "payment/payment-form";
    }

    @PostMapping("/checkout-pay")
    @ResponseBody
    public String processCheckout(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.makePayment(paymentRequest);
    }
}
