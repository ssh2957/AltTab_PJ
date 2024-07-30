package com.ssafy.alt_tab.drawing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alt_tab.drawing.dto.DrawingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            DrawingRequestDto drawingRequestDto = objectMapper.readValue(publishMessage, DrawingRequestDto.class);
            messagingTemplate.convertAndSend("/sub/api/v1/rooms/" + drawingRequestDto.getRoomId(), drawingRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}