package com.rabbitmq.hello.step7;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MessageProducer {

    private final StockRepository stockRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void sendMessage(final Stock stock, final String testCase) {
        rabbitTemplate.execute(channel -> {
            try {
                channel.txSelect(); // 트랜잭션 시작
                stock.setProcessed(false);
                stock.setCreateAt(LocalDateTime.now());
                stock.setUpdatedAt(LocalDateTime.now());
                Stock savedStock = stockRepository.save(stock);

                System.out.println("Stock Saved : " + savedStock);

                // message publish
                rabbitTemplate.convertAndSend("transactionQueue", stock);

                Thread.sleep(10000);

                if ("fail".equalsIgnoreCase(testCase)) {
                    throw new RuntimeException("transaction exception");
                }

                channel.txCommit();
                System.out.println("Transaction succeed");
            } catch (Exception e) {
                System.out.println("Transaction Failed");
                channel.txRollback();
                throw new RuntimeException("Transaction has been roll_backed");
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        });
    }
}
