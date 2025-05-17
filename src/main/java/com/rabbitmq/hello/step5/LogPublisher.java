package com.rabbitmq.hello.step5;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(LogRoutingKey routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey.name(), message);
        System.out.println("message published : " + routingKey + ":" + message);
    }
}
