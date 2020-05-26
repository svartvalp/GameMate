package com.svartvalp.GameMate.Models;

public class ChatWIthPublicKeyMessage {
    private Chat chat;
    private String publicKey;

    public ChatWIthPublicKeyMessage(Chat chat, String publicKey) {
        this.chat = chat;
        this.publicKey = publicKey;
    }

    public ChatWIthPublicKeyMessage() {
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
