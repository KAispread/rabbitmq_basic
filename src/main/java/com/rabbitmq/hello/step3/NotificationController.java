package com.rabbitmq.hello.step3;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationPublisher publisher;

    public NotificationController(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public String sendNotification(@RequestBody String message) {
        publisher.publish(message);
        return "[#] Notification sent: " + message + "\n";
    }

    @PostMapping("/progress")
    public String sendNotificationDuration(@RequestBody String message) {
        System.out.println("progress" + message);
        return message;
    }
}
