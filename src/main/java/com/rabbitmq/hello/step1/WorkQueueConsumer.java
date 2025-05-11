package com.rabbitmq.hello.step1;

import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {

    public void workQueueTask(String message) {
        String[] messageParts = message.split("\\|");
        String originMessage = messageParts[0];
        int duration = Integer.parseInt(messageParts[1]);

        System.out.println("# Received: " + originMessage + " (duration: " + duration + "ms)");

        try {
            System.out.println("now.. thread sleep time" + duration + "ms");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("# Completed: " + originMessage);
    }
}
