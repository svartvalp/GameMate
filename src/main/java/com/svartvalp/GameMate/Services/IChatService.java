package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IChatService {
    Flux<Chat> getUserChatsByNickname(String nickname);
    Mono<Chat> createChat(Chat chat);
    Mono<Void> deleteChat(String chatId, String ownerNickname);
    Flux<Chat> getLastChats(int page, int size);
    Flux<Chat> getChatsByGames(int page, int size, List<String> gameIds);
    Flux<Chat> getAllChats();
    Mono<Chat> getChatById(String id);
}
