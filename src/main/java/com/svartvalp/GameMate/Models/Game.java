package com.svartvalp.GameMate.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Game {
    @Id
    private String id;
    private String name;
    private String description;
}
