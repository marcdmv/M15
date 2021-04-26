package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name="games")
public class Games {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="dice_a", nullable=false, length=1)
    private int diceA;

    @Column(name="dice_b", nullable = false, length=1)
    private int diceB;

    @Column(name="player_id", nullable = false, length=11)
    private long playerId;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getDiceA() {
        return diceA;
    }
    public void setDiceA(int diceA) {
        this.diceA = diceA;
    }
    public int getDiceB() {
        return diceB;
    }
    public void setDiceB(int diceB) {
        this.diceB = diceB;
    }
    public long getplayerId() {
        return playerId;
    }
    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}