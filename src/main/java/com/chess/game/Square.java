package com.chess.game;

public class Square {

    public final int x;
    public final int y;

    public Square(int positionIndex) {
        this.x = positionIndex % 8;
        this.y = positionIndex / 8;
    }

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPositionIndex() {
        return y * 8 + x;
    }

    public boolean isOnBoard(){
        return  x >= 0 && x < 8 &&
                y >= 0 && y < 8;
    }

    public Square nextY(int stepY) {
        return new Square(x, y - stepY);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Square)) return false;
        Square that = (Square) obj;
        return  this.x == that.x &&
                this.y == that.y;
    }

}
