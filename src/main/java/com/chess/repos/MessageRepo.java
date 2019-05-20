package com.chess.repos;

import com.chess.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<ChatMessage, Long> {
}
