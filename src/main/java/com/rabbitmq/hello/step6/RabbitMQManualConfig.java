package com.rabbitmq.hello.step6;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;

/**
* RabbitMQ manual mode
* */
//@EnableRabbit
//@Configuration
public class RabbitMQManualConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // set manual
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
