package ru.vse.shop.gateway.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.vse.shop.kafka.MessageHandler;
import org.vse.shop.kafka.utils.Json;
import ru.vse.shop.dto.PushDto;

public class PushDtoMessageHandler implements MessageHandler<PushDto> {
    private static final Logger log = LoggerFactory.getLogger(PushDtoMessageHandler.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PushDtoMessageHandler(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(PushDto pushDto) {
        var userId = pushDto.getUserId();
        var msg = Json.toJson(pushDto);
        simpMessagingTemplate.convertAndSendToUser(userId, "/queue/messages", msg);
        log.info("Push message sent: {}", msg);
    }
}
