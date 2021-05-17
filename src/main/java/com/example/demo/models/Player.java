package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.List;

//@Entity
//@Table(name="players")
@Document (collection = "players")
public class Player {

    @Id
    //@Column(name="id")
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Field (name = "id")
    private long id;

    //@Column(name="name", nullable=false, length=100)
    @Field (name = "name")
    private String name;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == "") {
            name = "ANÒNIM";
        }
        ;
        this.name = name;
    }
}
