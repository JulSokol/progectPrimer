package com.chess.domain;

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

}
