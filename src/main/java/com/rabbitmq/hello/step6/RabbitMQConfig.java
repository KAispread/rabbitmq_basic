package com.rabbitmq.hello.step6;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

//@Configuration
public class RabbitMQConfig {

    // 큐, 교환기, 라우팅 키 이름 정의
    public static final String ORDER_COMPLETED = "orderCompletedQueue";
    public static final String ORDER_TOPIC = "orderExchange";
    public static final String DLQ = "deadLetterQueue";
    public static final String DLX = "deadLetterExchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter";

    // 원래 큐에 연결된 Topic Exchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_TOPIC);
    }

    // Dead Letter Exchange
    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX);
    }

    // 원래 큐 설정
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_COMPLETED)
            .withArgument("x-dead-letter-exchange", DLX) // DLX 설정
            .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY) // DLQ로 이동할 라우팅 키 설정
            .build();
    }

    // Dead Letter Queue 설정
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    // 원래 큐와 Exchange 바인딩
    @Bean
    public Binding orderQueueBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed");
    }

    // Dead Letter Queue와 Dead Letter Exchange 바인딩
    @Bean
    public Binding deadLetterQueueBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }
}
