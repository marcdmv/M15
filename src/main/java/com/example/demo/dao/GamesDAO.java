package com.example.demo.dao;

import com.example.demo.models.Games;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamesDAO extends JpaRepository<Games, Long> {

    List<Games> findGamesByPlayerId(Long playerId);

    void deleteGamesByPlayerId(Long playerId);
}