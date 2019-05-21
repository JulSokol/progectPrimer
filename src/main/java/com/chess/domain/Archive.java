package com.chess.domain;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "archive_game")
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public User white;

    @ManyToOne
    public User black;

    @ManyToOne
    public User vinUser;

    public LocalDateTime gameTime;

    public Long gameId;

    public Archive() { }

    public Archive(User white, User black, LocalDateTime gameTime, Long gameId) {
        this.white = white;
        this.black = black;
        this.gameTime = gameTime;
        this.gameId = gameId;
    }
}
