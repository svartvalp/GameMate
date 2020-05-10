package com.svartvalp.GameMate.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Chat {
    @Id
    private String id;

    private String description;

    private String title;

    private String ownerId;
}
