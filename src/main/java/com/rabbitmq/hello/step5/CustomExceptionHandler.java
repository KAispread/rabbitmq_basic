package com.rabbitmq.hello.step5;

import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandler {

    private final LogPublisher logPublisher;

    public CustomExceptionHandler(LogPublisher logPublisher) {
        this.logPublisher = logPublisher;
    }

    // error or log
    public void handleMessage(Exception e) {
        String message = e.getMessage();

        LogRoutingKey routingKey;

        if (e instanceof NullPointerException) {
            routingKey = LogRoutingKey.ERROR;
        } else if (e instanceof IllegalArgumentException) {
            routingKey = LogRoutingKey.WARN;
        } else {
            routingKey = LogRoutingKey.ERROR;
        }

        logPublisher.publish(routingKey, "Exception has been occur");
    }

    // process message
    public void handleMessage(String message) {
        LogRoutingKey routingKey = LogRoutingKey.INFO;
        logPublisher.publish(routingKey, "Info Log : " + message);
    }
}
