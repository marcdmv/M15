package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//import javax.persistence.*;

//@Entity
//@Table(name="games")
@Document(collection = "games")
public class Games {

    @Id
    //@Column(name="id")
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    //@Column(name="dice_a", nullable=false, length=1)
    @Field (name = "dice_a")
    private int diceA;

    //@Column(name="dice_b", nullable = false, length=1)
    @Field (name = "dice_b")
    private int diceB;

    //@Column(name="player_id", nullable = false, length=11)
    @Field (name = "player_id")
    private long playerId;

    public Games() {
    }

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
