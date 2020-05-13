package com.svartvalp.GameMate.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;


@Document
public class Chat {
    @Id
    private String id;

    private String description;

    private String title;

    private String ownerNickname;

    private long timeToLive;

    private long creationTime;

    private List<String> gameIds;

    public Chat(String description, String title, String ownerNickname, long timeToLive, long creationTime) {
        this.description = description;
        this.title = title;
        this.ownerNickname = ownerNickname;
        this.timeToLive = timeToLive;
        this.creationTime = creationTime;
        this.gameIds = new LinkedList<>();
    }

    public Chat(String description, String title, String ownerNickname, long timeToLive, long creationTime, List<String> gameIds) {
        this.description = description;
        this.title = title;
        this.ownerNickname = ownerNickname;
        this.timeToLive = timeToLive;
        this.creationTime = creationTime;
        this.gameIds = gameIds;
    }

    public Chat() {
    }

    public List<String> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<String> gameIds) {
        this.gameIds = gameIds;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }
}
