package com.chess.controller;

import com.chess.domain.User;
import com.chess.service.ChessGameService;
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
    @Autowired private ChessGameService chessGameService;

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

            List<Long> players = Arrays.asList(first, second);
            Collections.shuffle(players);
            User whitePlayer = userService.getById(asList.get(0));
            User blackPlayer = userService.getById(asList.get(1));
            chessGameService.createGame(whitePlayer, blackPlayer);
        }
    }

    @ResponseBody
    @GetMapping("/lobby/hasCurrentGame")
    public Long hasGame(Principal principal){
        User user = userService.findByUsername(principal.getName());
        return user.getCurrentGameId();
    }

    // проверяем, что есть созданная игра
    // если есть - возвращаетм ее ID
    // и делаем редирект на "chess?gameId=5"


}
