package com.chess.service;


import com.chess.domain.ChatMessage;
import com.chess.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServise {

    @Autowired
    private MessageRepo messageRepo;

    public void save(ChatMessage message) {
        messageRepo.save(message);
    }
}
