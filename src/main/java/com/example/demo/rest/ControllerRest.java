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

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player newPlayer = playersDAO.save(player);
        return ResponseEntity.ok(newPlayer);
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

    @PostMapping(value="/{playerId}/games")
    public ResponseEntity<Games> createGame(@RequestBody Games game, @PathVariable("playerId") Long playerId) throws Exception{
        if (game.getDiceA() > 6 || 0 > game.getDiceA()) throw new Exception("The Dice A cannot be " + game.getDiceA());
        if (game.getDiceB() > 6 || 0 > game.getDiceB()) throw new Exception("The Dice A cannot be " + game.getDiceA());
        Optional<Player> optionalPlayer = playersDAO.findById(playerId);
        if (optionalPlayer.isPresent()) {
            game.setPlayerId(playerId);
            Games newGame = gamesDAO.save(game);
            return ResponseEntity.ok(newGame);
        } else {
            throw new Exception("No player found for id: " + playerId);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlayers() {
        playersDAO.deleteAll();
        return ResponseEntity.ok(null);
    }

    @DeleteMapping(value="/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable("playerId") Long playerId) throws Exception{
        Optional<Player> optionalPlayer = playersDAO.findById(playerId);
        if (optionalPlayer.isPresent()) {
            playersDAO.deleteById(playerId);
            return ResponseEntity.ok(null);
        } else {
            throw new Exception("No player to delete by id: " + playerId);
        }
    }

    @Transactional
    @DeleteMapping(value="/{playerId}/games")
    public ResponseEntity<Void> deleteShopPictures(@PathVariable("playerId") Long playerId) throws Exception{
        Optional<Player> optionalPlayer = playersDAO.findById(playerId);
        if (optionalPlayer.isPresent()) {
            gamesDAO.deleteGamesByPlayerId(playerId);
            return ResponseEntity.ok(null);
        } else {
            throw new Exception("No player found for id: " + playerId);
        }
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = playersDAO.findAll();
        for (Player player : players) {
            List<Games> games = gamesDAO.findGamesByPlayerId(player.getId());
            float counter = 0;
            float gamesPlayed = games.size();
            for (Games game : games) {
                if (game.getDiceA() + game.getDiceB() == 7) {
                    counter ++;
                }
            }

            float winRate = counter/gamesPlayed;
            System.out.println("Player id: " + player.getId() + " | " + "Player name: " + player.getName() + " | " +
                    "Porcentaje de éxito: " + winRate*100 + "%");
        }
        return ResponseEntity.ok(players);
    }

    @GetMapping(value="/{playerId}/games")
    public ResponseEntity<List<Games>> getPlayerGames(@PathVariable("playerId") Long playerId) {
        List<Games> games = gamesDAO.findGamesByPlayerId(playerId);
        Optional<Player> optionalPlayer = playersDAO.findById(playerId);
        Player player = optionalPlayer.get();
        System.out.println("Player id: " + playerId + " | Player name: " + player.getName());
        for (Games game : games) {
            System.out.println("Game id: " + game.getId() + " | " + "Dice A: " + game.getDiceA() + " | " +
                    "Dice B: " + game.getDiceB());
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping(value="/ranking") //retorna el ranking mig de tots els jugadors del sistema. És a dir, el percentatge mig d’èxits.
    public ResponseEntity<List<Player>> getPlayerRanking() {
        List<Player> players = playersDAO.findAll();
        float counter = 0;
        float gamesPlayed = 0;
        for (Player player : players) {
            List<Games> games = gamesDAO.findGamesByPlayerId(player.getId());
            gamesPlayed = gamesPlayed + games.size();
            for (Games game : games) {
                if (game.getDiceA() + game.getDiceB() == 7) {
                    counter++;
                }
            }
        }
        float winRate = counter/gamesPlayed;
        System.out.println("Porcentaje de éxito: " + winRate*100 + "%");
        return null;
    }

    @GetMapping(value="/ranking/loser") //retorna el jugador amb pitjor percentatge d’èxit
    public ResponseEntity<Player> getPlayerLoser() {
        List<Player> players = playersDAO.findAll();
        float worstRate = 1;
        Player loserPlayer = players.get(0);
        for (Player player : players) {
            List<Games> games = gamesDAO.findGamesByPlayerId(player.getId());
            float counter = 0;
            float gamesPlayed = games.size();
            for (Games game : games) {
                if (game.getDiceA() + game.getDiceB() == 7) {
                    counter ++;
                }
            }

            float winRate = counter/gamesPlayed;
            if (winRate < worstRate) {
                loserPlayer = player;
                worstRate = winRate;
            }
        }
        System.out.println("LOSER | Player id: " + loserPlayer.getId() + " | " + "Player name: " + loserPlayer.getName() + " | " +
                "Porcentaje de éxito: " + worstRate*100 + "%");
        return null;
    }

    @GetMapping(value="/ranking/winner") //retorna el jugador amb pitjor percentatge d’èxit
    public ResponseEntity<Player> getPlayerWinner() {
        List<Player> players = playersDAO.findAll();
        float bestRate = 0;
        Player winnerPlayer = players.get(0);
        for (Player player : players) {
            List<Games> games = gamesDAO.findGamesByPlayerId(player.getId());
            float counter = 0;
            float gamesPlayed = games.size();
            for (Games game : games) {
                if (game.getDiceA() + game.getDiceB() == 7) {
                    counter ++;
                }
            }

            float winRate = counter/gamesPlayed;
            if (winRate > bestRate) {
                winnerPlayer = player;
                bestRate = winRate;
            }
        }
        System.out.println("WINNER | Player id: " + winnerPlayer.getId() + " | " + "Player name: " + winnerPlayer.getName() + " | " +
                "Porcentaje de éxito: " + bestRate*100 + "%");
        return null;
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
