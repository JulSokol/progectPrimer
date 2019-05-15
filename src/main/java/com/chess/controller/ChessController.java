package com.chess.controller;

import com.chess.domain.ChessGame;
import com.chess.service.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    @Autowired
    public ChessGameService chessGameService;

    @GetMapping("getFigures")
    public String getFigures (
            @RequestParam(value = "gameId") long gameId
    ){
        ChessGame board = chessGameService.getGame(gameId);
        return board.figures;
    }

    @GetMapping("moveFigure")
    public String moveFigure (
            @RequestParam(value = "gameId") long gameId,
            @RequestParam(value = "frCoord") int frCoord,
            @RequestParam(value = "toCoord") int toCoord) {
        ChessGame board = chessGameService.move(gameId, frCoord, toCoord);
        return board.figures;
    }

    @GetMapping("newFigures")
    public String newFigures() {
        ChessGame chessGame = chessGameService.newGame();
        return chessGame.id.toString();
    }


}
