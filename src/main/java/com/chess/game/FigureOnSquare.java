package com.chess.game;

public class FigureOnSquare {
    public FigureOnSquare(Square square, char figure) {
        this.square = square;
        this.figure = figure;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public char getFigure() {
        return figure;
    }

    public void setFigure(char figure) {
        this.figure = figure;
    }

    private Square square;
    private char figure;

}
