package com.chess.controller;


import com.chess.domain.ChatMessage;
import com.chess.domain.User;
import com.chess.service.ChatServise;
import com.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
public class MessageController {

    @Autowired
    private ChatServise chatServise;
    @Autowired
    private UserService userService;

    @GetMapping("/chat/add")
    public String chat(Principal principal, Model model,
                       @RequestParam("message") String messageText){
        User user = userService.findByUsername(principal.getName());
        ChatMessage message = new ChatMessage();
        message.setUser(user);
        message.setMessage(messageText);
        message.setMessageTime(LocalDateTime.now());
        chatServise.save(message);
        model.addAttribute("ChatMessage", message);

        return "chat";
    }


    @ResponseBody
    @GetMapping("chat/user")
    public void findGame(Principal principal, Model model){
//        chatServise.save(message);
    }

}
