package com.rabbitmq.hello.step4;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
//@RestController
public class NewsApiController {

    private final NewsPublisher newsPublisher;

    @PostMapping("/news/api/publish")
    public String send(@RequestParam String newsType) {
        newsPublisher.publishAPI(newsType);
        return newsType;
    }
}
