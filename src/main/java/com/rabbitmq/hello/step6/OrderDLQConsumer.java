package com.rabbitmq.hello.step6;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
//@Component
public class OrderDLQConsumer {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void process(String message) {
        System.out.println("DLQ Message Received: " + message);

        try {
            String fixMessage = "success";

            rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_TOPIC,
                "order.completed.shipping",
                fixMessage);

            System.out.println("DLQ Message Sent: " + fixMessage);
        } catch (Exception e) {
            System.err.println("### [DLQ Consumer Error] " + e.getMessage());
        }
    }
}
