package com.example.demo.dao;

import com.example.demo.models.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GamesDAO extends MongoRepository<Games, Long> {

    List<Games> findGamesByPlayerId(Long playerId);

    void deleteGamesByPlayerId(Long playerId);
}