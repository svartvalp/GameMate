package com.svartvalp.GameMate.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;


@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Indexed(unique = true, name = "unique_email")
    private String email;
    @Indexed(unique = true, name = "unique_nickname")
    private String nickname;
    private String password;
    @JsonIgnore
    private List<String> chatIds;

    public User(String email,String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        chatIds = new LinkedList<>();
    }

    public User() {
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }
    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getChatIds() {
        return chatIds;
    }

    public void setChatIds(List<String> chatIds) {
        this.chatIds = chatIds;
    }

    @Override
    public String toString() {
        return email + " " + nickname + " " + password;
    }
}
