package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.ChatMessage;
import com.svartvalp.GameMate.Repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChatMessageService implements IChatMessageService {

    private ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public Flux<ChatMessage> findAllMessagesByChatId(String chatId) {
        return chatMessageRepository.findAllByChatId(chatId);
    }

    @Override
    public Flux<ChatMessage> findLastMessagesByChatId(String chatId, int size, int pageNumber) {
        return chatMessageRepository
                .findAllByChatId(chatId, PageRequest.of(pageNumber, size,
                        Sort.by(Sort.Direction.DESC, "creationTime")));
    }

    @Override
    public Mono<ChatMessage> createMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public void deleteMessage(ChatMessage message) {
        chatMessageRepository.delete(message);
    }
}
