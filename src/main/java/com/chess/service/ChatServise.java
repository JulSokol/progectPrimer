package com.chess.service;


import com.chess.domain.ChatMessage;
import com.chess.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServise {

    @Autowired
    private MessageRepo messageRepo;

    public ChatMessage save(ChatMessage message) {
        return messageRepo.save(message);
    }

    public List<ChatMessage> findAll() {
        return messageRepo.findAll();
    }

    public List<ChatMessage> findMessages(Long gameId) {
        return messageRepo.findByGameIdAndMessageTimeAfter(gameId, LocalDateTime.now().minusHours(1));
    }
}
