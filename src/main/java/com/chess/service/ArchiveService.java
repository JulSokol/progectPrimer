package com.chess.service;


import com.chess.domain.Archive;
import com.chess.repos.ArchiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {

    @Autowired
    private ArchiveRepo archiveRepo;

    public void save(Archive gameId){
        archiveRepo.save(gameId);
    }
}
