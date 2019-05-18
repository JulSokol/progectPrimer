package com.chess.service;

import com.chess.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User getById(Long id);
}