package com.chess.repos;

import com.chess.domain.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepo extends JpaRepository<Archive, Long> {
}
