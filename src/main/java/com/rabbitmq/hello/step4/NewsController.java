package com.rabbitmq.hello.step4;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
//@Controller
public class NewsController {

    private final NewsPublisher newsPublisher;

    @MessageMapping("/subscribe")
    public void sendMessage(@Header("newsType") String newsType) {
        System.out.println("[#] newsType: " + newsType);

        String newsMessage = newsPublisher.publish(newsType);
        System.out.println("[#] newsMessage: " + newsMessage);
    }
}
