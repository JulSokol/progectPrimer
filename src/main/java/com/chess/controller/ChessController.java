package com.chess.controller;

import com.chess.domain.ChessTable;
import com.chess.repos.ChessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    @Autowired
    public ChessRepo chessRepo;

    static ChessTable board = null;

    @GetMapping("getFigures")
    public String getFigures (){
        if (board == null){
            return "";
        }
        return board.figures;
    }

    @GetMapping("moveFigure")
    public String moveFigure (
            @RequestParam(value = "frCoord") int frCoord,
            @RequestParam(value = "toCoord") int toCoord) {

        if (canMove(frCoord, toCoord)){
            StringBuilder figuresBuilder = new StringBuilder(board.figures);
            char figure = figuresBuilder.charAt(frCoord);
            figuresBuilder.setCharAt(frCoord, '1');
            figuresBuilder.setCharAt(toCoord, figure);
            board.figures = figuresBuilder.toString();
            chessRepo.save(board);
        }
        return board.figures;
    }

    @GetMapping("newFigures")
    public String newFigures() {
        board = new ChessTable();
        board.figures = "rnbqkbnrpppppppp11111111111111111111111111111111PPPPPPPPRNBQKBNR";
        chessRepo.save(board);
        return board.figures;
    }

    boolean canMove(int frFigure, int toFigure){
        if("kK".indexOf(board.figures.charAt(toFigure)) >=0){
            return false;
        }

        if (colorFigures(frFigure).equals(colorFigures(toFigure))){
            return false;
        }

        return true;

    }

    public String colorFigures(int figure) {
        if ("KQRBNP".indexOf(board.figures.charAt(figure)) >= 0) {
            return "white";
        }
        if ("kqrbnp".indexOf(board.figures.charAt(figure)) >=0){
            return "black";
        }
        return "empty";
    }

}
