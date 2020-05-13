package com.svartvalp.GameMate.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Game {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;

    private String description;

    public Game(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
