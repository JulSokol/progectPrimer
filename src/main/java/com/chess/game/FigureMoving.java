package com.chess.game;

public class FigureMoving {

    public final char figure;
    public final Square from;
    public final Square to;

    public FigureMoving(char figure, int frCoord, int toCoord) {
        this.figure = figure;
        this.from = new Square(frCoord);
        this.to = new Square(toCoord);
    }

    public int getDeltaX () {
        return to.x - from.x;
    }
    public int getDeltaY (){
        return from.y - to.y;
    }

    public int getAbsDeltaX (){
        return Math.abs(getDeltaX());
    }
    public int getAbsDeltaY(){
        return Math.abs(getDeltaY());
    }

    public int getSignX() {
        return (int) Math.signum(getDeltaX());
    }

    public int getSignY() {
        return (int) -Math.signum(getDeltaY());
    }

}
