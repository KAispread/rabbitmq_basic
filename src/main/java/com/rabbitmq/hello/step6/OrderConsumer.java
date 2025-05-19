package com.rabbitmq.hello.step6;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private static final int MAX_RETRY = 3; // max retry count
    private static int retryCount = 0;

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED, containerFactory = "rabbitListenerContainerFactory")
    public void processOrder(String message, Channel channel, @Header("amqp_deliveryTag") long tag) {
        try {
            // fail case
            if ("fail".equalsIgnoreCase(message)) {
                handleFail(message, channel, tag);
            }
            // success case
            System.out.println("# succeed : " + message);
            channel.basicAck(tag, false);
            retryCount = 0;
        } catch (Exception e) {
            doBasicReject(channel, tag, e);
        }
    }

    private void doBasicReject(Channel channel, long tag, Exception e) {
        System.err.println("# raise error : " + e.getMessage());
        try {
            // basicReject when fail
            channel.basicReject(tag, true);
        } catch (IOException ex) {
            System.err.println("# fail & reject message : " + ex.getMessage());
        }
    }

    private void handleFail(String message, Channel channel, long tag) throws IOException {
        if (retryCount < MAX_RETRY) {
            System.err.println("#### Fail & Retry = " + retryCount);
            retryCount++;
            throw new RuntimeException(message);
        }

        System.err.println("#### Over handled MAX RETRY COUNT");
        retryCount = 0;
        channel.basicNack(tag, false, false);
    }
}
