package com.rabbitmq.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam String value) {
        return "Hello %s".formatted(value);
    }
}
