package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IChatService {
    public Flux<Chat> getUserChatsByNickname(String nickname);
    public Mono<Chat> createChat(Chat chat);
    public Mono<Void> deleteChat(String chatId, String ownerNickname);
    public Flux<Chat> getLastChats(int page, int size);
    public Flux<Chat> getChatsByGames(int page, int size , List<String> gameIds);
    public Flux<Chat> getAllChats();
}
