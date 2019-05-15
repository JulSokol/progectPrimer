package com.chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChessBoardController {
    @GetMapping("chess")
    public String chess(){
        return "chess";
    }
}
