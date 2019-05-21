package com.chess.controller;


import com.chess.domain.ChatMessage;
import com.chess.domain.User;
import com.chess.service.ChatServise;
import com.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private ChatServise chatServise;
    @Autowired
    private UserService userService;

    @PostMapping("/chat/add")
    public ChatMessage addMessage(Principal principal, @RequestBody AddMsg addMsg){
        User user = userService.findByUsername(principal.getName());
        ChatMessage message = new ChatMessage();
        message.setUser(user);
        message.setGameId(addMsg.gameId);
        message.setMessage(addMsg.message);
        message.setMessageTime(LocalDateTime.now());
        return chatServise.save(message);
    }

    @GetMapping("chat/messages")
    public List<ChatMessage> chatMessages(
            @RequestParam(value = "gameId", required = false) Long gameId
    ){
        return chatServise.findMessages(gameId);
    }

    public static class AddMsg {
        public String message;
        public Long gameId;
    }

}
