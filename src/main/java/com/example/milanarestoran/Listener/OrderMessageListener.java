package com.example.milanarestoran.Listener;

import com.example.milanarestoran.config.RabbitMQConfig;
import com.example.milanarestoran.model.Order;
import com.example.milanarestoran.pojo.OrderMessage;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(OrderMessage orderMessage) {
        System.out.println("Получен заказ для order-queue на сумму " + orderMessage.getTotalAmount() + " по адресу доставки " + orderMessage.getDeliveryAddress());


    }



}
