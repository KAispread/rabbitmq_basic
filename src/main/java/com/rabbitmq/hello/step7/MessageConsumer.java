package com.rabbitmq.hello.step7;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageConsumer {

    private final StockRepository stockRepository;

    @RabbitListener(queues = "transactionQueue")
    public void receiveTransaction(final Stock stock) {
        System.out.println("# received message : " + stock);

        try {
            stock.setProcessed(true);
            stock.setUpdatedAt(LocalDateTime.now());
            stockRepository.save(stock);
            Thread.sleep(5000);
            System.out.println("# Complete to save Stock");
        } catch (Exception e) {
            System.out.println("# Error modify Entity" + e.getMessage());
            throw new RuntimeException("fail to consume"); // injection fail message to dead letter queue
        }
    }
}
