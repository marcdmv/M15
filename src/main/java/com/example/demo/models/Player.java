package com.example.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="players")
public class Player {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable=false, length=100)
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
