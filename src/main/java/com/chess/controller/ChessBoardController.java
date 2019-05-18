package com.chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChessBoardController {
    @GetMapping("chess/{id}")
    public String chess(
            Model model,
            @PathVariable("id") int id
    ){
        model.addAttribute("gameId", id);
        return "chess";
    }
}
