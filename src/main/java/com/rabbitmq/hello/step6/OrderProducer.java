package com.rabbitmq.hello.step6;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

//@Component
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendShipping(String message) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_TOPIC,
            "order.completed",
            message);
        System.out.println("[주문 완료. 배송 지시 메시지 생성 : " + message + "]");
    }
}
