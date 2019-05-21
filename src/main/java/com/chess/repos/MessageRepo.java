package com.chess.repos;

import com.chess.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepo extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByGameIdAndMessageTimeAfter(Long gameId, LocalDateTime dateTime);
}
