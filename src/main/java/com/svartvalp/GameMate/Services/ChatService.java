package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Exceptions.AuthenticationException;
import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Repositories.ChatMessageRepository;
import com.svartvalp.GameMate.Repositories.ChatRepository;
import com.svartvalp.GameMate.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.Instant;
import java.util.List;

@Component
public class ChatService implements IChatService{

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Scheduled(fixedRateString = "${scheduling.fixedRate.in.milliseconds}")
    public void doScheduled() {
        Iterable<Chat> chats = chatRepository.findAll().filter(chat -> !checkChatLifeTime(chat))
                .toIterable();
        chatRepository.deleteAll(chats).block();
        for(Chat chat : chats) {
            chatMessageRepository.deleteAll(chatMessageRepository.findAllByChatId(chat.getId()).toIterable()).block();
        }
    }


    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository, ChatMessageRepository chatMessageRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public Flux<Chat> getUserChatsByNickname(String nickname) {
        return userRepository.findByNickname(nickname).flatMapMany(user ->
                chatRepository.findAllByIdIn(user.getChatIds())
                        .filter(this::checkChatLifeTime));
    }

    @Override
    public Mono<Chat> createChat(Chat chat) {
        chat.setCreationTime(Instant.now().toEpochMilli());
        return chatRepository.insert(chat);
    }

    @Override
    public Mono<Void> deleteChat(String chatId, String ownerNickname) {
        return chatRepository.findById(chatId)
                .flatMap(chat -> chat.getOwnerNickname().equals(ownerNickname) ? chatRepository.delete(chat):
                         Mono.error(new AuthenticationException("Chat can delete only owner")));
    }

    @Override
    public Flux<Chat> getLastChats(int page, int size) {
        return chatRepository
                .findAllByIdExists(true,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationTime")))
                .filter(this::checkChatLifeTime);
    }

    @Override
    public Flux<Chat> getChatsByGames(int page, int size , List<String> gameIds) {
        return chatRepository.findAllByGameIdsIsContaining(gameIds,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationTime")))
                .filter(this::checkChatLifeTime);
    }

    @Override
    public Flux<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public Mono<Chat> getChatById(String id) {
        return chatRepository.findById(id);
    }

    public boolean checkChatLifeTime(Chat chat) {
        return chat.getCreationTime() + chat.getTimeToLive() > Instant.now().toEpochMilli();
    }


}
