package com.example.demo.dao;

import com.example.demo.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersDAO extends JpaRepository<Player, Long> {
}
