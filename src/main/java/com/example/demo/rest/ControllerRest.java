package com.example.demo.rest;

import java.util.*;
import com.example.demo.dao.PlayersDAO;
import com.example.demo.dao.GamesDAO;
import com.example.demo.models.Games;
import com.example.demo.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class ControllerRest {

    @Autowired
    private PlayersDAO playersDAO;
    @Autowired
    private GamesDAO gamesDAO;

    @GetMapping
    public ResponseEntity<List<Player>> getPlayer() {
        List<Player> players = playersDAO.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping(value="/{playerId}/games")
    public ResponseEntity<List<Games>> getPlayerGames(@PathVariable("playerId") Long playerId) {
        List<Games> games = gamesDAO.findGamesByPlayerId(playerId);
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player newPlayer = playersDAO.save(player);
        return ResponseEntity.ok(newPlayer);
    }

    @PostMapping(value="/{playerId}/games")
    public ResponseEntity<Games> createGame(@RequestBody Games game, @PathVariable("playerId") Long playerId) {
        game.setPlayerId(playerId);
        Games newGame = gamesDAO.save(game);
        return ResponseEntity.ok(newGame);
    }

    @DeleteMapping(value="/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable("playerId") Long playerId) {
        playersDAO.deleteById(playerId);
        return ResponseEntity.ok(null);
    }

    @Transactional
    @DeleteMapping(value="/{playerId}/games")
    public ResponseEntity<Void> deleteShopPictures(@PathVariable("playerId") Long playerId) {
        gamesDAO.deleteGamesByPlayerId(playerId);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        Optional<Player> optionalPlayer = playersDAO.findById(player.getId());
        if (optionalPlayer.isPresent()) {
            Player updatePlayer = optionalPlayer.get();
            updatePlayer.setName(player.getName());
            playersDAO.save(updatePlayer);
            return ResponseEntity.ok(updatePlayer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("playerId") Long playerId) {
        Optional<Player> optionalPlayer = playersDAO.findById(playerId);
        if (optionalPlayer.isPresent()) {
            return ResponseEntity.ok(optionalPlayer.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
