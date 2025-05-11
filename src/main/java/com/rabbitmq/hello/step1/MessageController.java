package com.rabbitmq.hello.step1;

import com.rabbitmq.hello.step0.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
//@RestController
public class MessageController {

    private final WorkQueueProducer producer;

    @PostMapping("/workqueue")
    public String sendMessage(@RequestParam String message, @RequestParam int duration) {
        producer.sendWorkQueue(message, duration);
        return "[#] Message sent successfully =  " + message + ", (" + duration + ") ms";
    }
}
