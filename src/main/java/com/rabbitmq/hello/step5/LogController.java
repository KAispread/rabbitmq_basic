package com.rabbitmq.hello.step5;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final CustomExceptionHandler customExceptionHandler;

    @GetMapping("/error")
    public ResponseEntity<String> errorAPI() {
        try {
            String value = null;
            value.toLowerCase();
        } catch (Exception e) {
            customExceptionHandler.handleMessage(e);
        }
        return ResponseEntity.ok("Controller NPE 처리");
    }

    @GetMapping("/warn")
    public ResponseEntity<String> warnAPI() {
        try {
            throw new IllegalArgumentException("IllegalArgumentException occur");
        } catch (Exception e) {
            customExceptionHandler.handleMessage(e);
        }
        return ResponseEntity.ok("Controller IllegalArgumentException 처리");
    }

    @GetMapping("/info")
    public ResponseEntity<String> infoAPI(String message) {
        customExceptionHandler.handleMessage(message);
        return ResponseEntity.ok("Controller Info log 발송 처리");
    }
}
