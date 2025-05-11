package com.rabbitmq.hello.step3;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class StompController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(NotificationMessage notificationMessage) {
        // 수신된 메시지를 브로드캐스팅
        String message = notificationMessage.message();

        System.out.println("[#] message = " + message);
        // 클라이언트에 메시지 브로드캐스트
        messagingTemplate.convertAndSend("/topic/notifications", message);

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
                System.out.println("message=sent");
                messagingTemplate.convertAndSendToUser(
                    "session_1", "/queue/notifications/progress", "waited " + i + " second(s)"
                );
            }
            System.out.println("message=end");
            messagingTemplate.convertAndSendToUser(
                "session_1", "/queue/notifications/progress", "Server work complete"
            );
        }).start();
    }
}
