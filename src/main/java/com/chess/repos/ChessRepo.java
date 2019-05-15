package com.chess.repos;

import com.chess.domain.ChessTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessRepo extends JpaRepository<ChessTable, Long> {

}
