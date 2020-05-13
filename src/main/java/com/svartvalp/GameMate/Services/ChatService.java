package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Repositories.ChatRepository;
import com.svartvalp.GameMate.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.List;

@Component
public class ChatService implements IChatService{

    ChatRepository chatRepository;
    UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Flux<Chat> getUserChatsByNickname(String nickname) {
        return userRepository.findByNickname(nickname).flatMapMany(user ->
                chatRepository.findAllByIdIn(user.getChatIds())
                        .filter(this::checkChatLifeTime));
    }

    @Override
    public Mono<Chat> createChat(Chat chat) {
        return chatRepository.insert(chat);
    }

    @Override
    public Mono<Void> deleteChat(String chatId, String ownerNickname) {
        return chatRepository.findById(chatId)
                .flatMap(chat -> chat.getOwnerNickname() == ownerNickname ? chatRepository.delete(chat)
                        : Mono.error(new AuthenticationException("Chat can delete only owner")));
    }

    @Override
    public Flux<Chat> getLastChats(int page, int size) {
        return chatRepository.findAllByIdExists(true,PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationTime")));
    }

    @Override
    public Flux<Chat> getChatsByGames(int page, int size , List<String> gameIds) {
        return chatRepository.findAllByGameIdsIsContaining(gameIds,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationTime")));
    }

    @Override
    public Flux<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    private boolean checkChatLifeTime(Chat chat) {
        return chat.getCreationTime() + chat.getTimeToLive() > Instant.now().toEpochMilli();
    }


}
