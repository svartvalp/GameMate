package com.svartvalp.GameMate.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class User {
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String nickname;
    private String password;

    public User(String email,String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
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
}
