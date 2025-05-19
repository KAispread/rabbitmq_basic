package com.rabbitmq.hello.step6;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_COMPLETED = "order_completed_queue";
    public static final String ORDER_EXCHANGE = "order_completed_exchange";
    public static final String DLQ = "deadLetterQueue";
    public static final String DLX = "deadLetterExchange";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX);
    }

    // move message to dead letter queue when fail to handling message
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_COMPLETED)
            .withArgument("x-dead-letter-exchange", DLX) // Dead letter queue
            .withArgument("x-dead-letter-routing-key", DLQ)
            .ttl(5000)
            .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ);
    }

    @Bean
    public Binding orderCompletedBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed.#");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ);
    }
}
