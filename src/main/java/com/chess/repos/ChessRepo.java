package com.chess.repos;

import com.chess.domain.ChessGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessRepo extends JpaRepository<ChessGame, Long> {

}
