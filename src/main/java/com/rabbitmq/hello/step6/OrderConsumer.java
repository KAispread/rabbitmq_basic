package com.rabbitmq.hello.step6;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
//@Component
public class OrderConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;

    //@RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED)
    public void consume(String message) {
        retryTemplate.execute(context -> {
            try {
                System.out.println("# received message : " + message + " # retry : " + context.getRetryCount());

                if ("fail".equalsIgnoreCase(message)) {
                    throw new RuntimeException();
                }
                System.out.println("# message retry success : " + message);
            } catch (Exception e) {
                if (context.getRetryCount() >= 2) {
                    rabbitTemplate.convertAndSend(
                        RabbitMQConfig.DLX,
                        RabbitMQConfig.DEAD_LETTER_ROUTING_KEY,
                        message);
                } else {
                    throw e;
                }
            }
            return null;
        });
    }
}
