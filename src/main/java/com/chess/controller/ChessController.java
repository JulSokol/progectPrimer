package com.chess.controller;

import com.chess.domain.ChessGame;
import com.chess.domain.User;
import com.chess.service.ChessGameService;
import com.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ChessController {

    @Autowired public UserService userService;
    @Autowired public ChessGameService chessGameService;

    @GetMapping("getFigures")
    public GameInfo getFigures(
            Principal principal,
            @RequestParam(value = "gameId") long gameId
    ){
        ChessGame board = chessGameService.getGame(gameId);
        GameInfo info = new GameInfo();
        info.figures = board.figures;
        info.moveColor = board.moveColor;
        if (principal.getName().equals(board.white.getUsername())) {
            info.yourColor = ChessGameService.COLOR_WHITE;
        } else if (principal.getName().equals(board.black.getUsername())) {
            info.yourColor = ChessGameService.COLOR_BLACK;
        }
        return info;
    }

    @GetMapping("moveFigure")
    public String moveFigure (
            Principal principal,
            @RequestParam(value = "gameId") long gameId,
            @RequestParam(value = "frCoord") int frCoord,
            @RequestParam(value = "toCoord") int toCoord) {
        User user = userService.findByUsername(principal.getName());
        ChessGame board = chessGameService.move(user, gameId, frCoord, toCoord);
        return board.figures;
    }

    @GetMapping("newFigures")
    public String newFigures() {
        ChessGame chessGame = chessGameService.newGame();
        return chessGame.id.toString();
    }

    public static class GameInfo {
        public String moveColor;
        public String yourColor;
        public String figures;
    }
}
