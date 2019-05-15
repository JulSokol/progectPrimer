package com.chess.controller;

import com.chess.domain.User;
import com.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.*;

@Controller
public class LobbyController {

    @Autowired private UserService userService;

    static Set<Long> usersInSearch = new HashSet<>();

    @GetMapping("/lobby")
    public String lobby(){
        return "lobby";
    }

    @ResponseBody
    @GetMapping("/lobby/findgame")
    public void findGame(Principal principal){
        User user = userService.findByUsername(principal.getName());
        usersInSearch.add(user.getId());
        if (usersInSearch.size()>=2){
            List<Long> asList = new ArrayList<>(usersInSearch);
            Collections.shuffle(asList);
            Long first = asList.get(0);
            Long second = asList.get(1);
            usersInSearch.remove(first);
            usersInSearch.remove(second);
        }
    }
}
