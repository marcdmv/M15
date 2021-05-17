package com.example.demo.dao;

import com.example.demo.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayersDAO extends MongoRepository<Player, Long> {
}
