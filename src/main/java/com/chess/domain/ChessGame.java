package com.chess.domain;

import com.chess.game.Square;

import javax.persistence.*;

@Entity
@Table(name = "chess_figures")
public class ChessGame {

    public String figures;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public User white;

    @ManyToOne
    public User black;

    public String moveColor;

    public char getFigure(Square square){
        return figures.charAt(square.getPositionIndex());
    }

}
