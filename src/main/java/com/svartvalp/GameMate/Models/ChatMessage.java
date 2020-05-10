package com.svartvalp.GameMate.Models;


import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonRootName("message")
@Document
public class ChatMessage {
    @Id
    private String id;
    private String text;
    private long creationTime;
    private String userNickName;
    private String chatId;

    public ChatMessage(String text, long creationTime, String userNickName, String chatId) {
        this.text = text;
        this.creationTime = creationTime;
        this.userNickName = userNickName;
        this.chatId = chatId;
    }

    public ChatMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
