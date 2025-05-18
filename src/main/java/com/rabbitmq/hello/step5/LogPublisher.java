package com.rabbitmq.hello.step5;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(LogRoutingKey routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey.pattern, message);
        System.out.println("message published : " + routingKey.pattern + " : " + message);
    }
}
