package com.chess.domain;

import javax.persistence.*;

@Entity
@Table(name = "chess_figures")
public class ChessTable {

    public String figures;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
}
