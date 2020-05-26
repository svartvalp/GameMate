package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Repositories.ChatMessageRepository;
import com.svartvalp.GameMate.Repositories.ChatRepository;
import com.svartvalp.GameMate.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ChatServiceTest {
    @MockBean
    private ChatRepository chatRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    ChatMessageRepository chatMessageRepository;

    @Test
    void createChat() {
        Chat chat = new Chat();
        ChatService chatService = new ChatService(chatRepository,userRepository, chatMessageRepository);
        Mockito.when(chatRepository.insert(chat)).thenReturn(Mono.just(chat));
        StepVerifier.create(chatService.createChat(chat))
                .expectNext(chat).verifyComplete();
        assertNotEquals(0, chat.getCreationTime());

    }

    @Test
    void checkChatLifeTime() {
        ChatService chatService = new ChatService(chatRepository,userRepository, chatMessageRepository);
        Chat chat = new Chat();
        chat.setTimeToLive(1000);
        chat.setCreationTime(Instant.now().toEpochMilli());
        assertEquals(true, chatService.checkChatLifeTime(chat));
    }
}