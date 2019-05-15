package com.chess.service;

import com.chess.domain.ChessGame;
import com.chess.repos.ChessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessGameService {

    @Autowired public ChessRepo chessRepo;

    public ChessGame newGame() {
        ChessGame board = new ChessGame();
        board.figures = "rnbqkbnrpppppppp11111111111111111111111111111111PPPPPPPPRNBQKBNR";
        chessRepo.save(board);
        return board;
    }

    public ChessGame move(long gameId, int frCoord, int toCoord) {
        ChessGame board = chessRepo.findById(gameId).get();
        if (canMove(board, frCoord, toCoord)){
            StringBuilder figuresBuilder = new StringBuilder(board.figures);
            char figure = figuresBuilder.charAt(frCoord);
            figuresBuilder.setCharAt(frCoord, '1');
            figuresBuilder.setCharAt(toCoord, figure);
            board.figures = figuresBuilder.toString();
            chessRepo.save(board);
        }
        return board;
    }

    private boolean canMove(ChessGame board, int frFigure, int toFigure){
        if("kK".indexOf(board.figures.charAt(toFigure)) >=0){
            return false;
        }

        if (colorFigures(board, frFigure).equals(colorFigures(board, toFigure))){
            return false;
        }

        return true;

    }

    private String colorFigures(ChessGame board, int figure) {
        if ("KQRBNP".indexOf(board.figures.charAt(figure)) >= 0) {
            return "white";
        }
        if ("kqrbnp".indexOf(board.figures.charAt(figure)) >=0){
            return "black";
        }
        return "empty";
    }

    public ChessGame getGame(long gameId) {
        return chessRepo.findById(gameId).get();
    }
}
