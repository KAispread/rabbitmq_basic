package com.rabbitmq.hello.step7;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/message")
@RestController
public class TransactionController {

    private final MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody Stock stock,
        @RequestParam(required = false, defaultValue = "success") String message) {
        System.out.println("Send message : " + stock);

        try {
            messageProducer.sendMessage(stock, message);
            return ResponseEntity.ok("Message Sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Transaction failed MQ : " + e.getMessage());
        }
    }
}
