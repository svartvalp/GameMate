package com.svartvalp.GameMate.Models;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class ModelsTest {

    @Test
    public void ChatMessageTest() {
        ChatMessage message = new ChatMessage("helo", 111, "abd", "ccc");
        Mono<ChatMessage> messageMono = Mono.just(message);
        StepVerifier.create(messageMono)
                .expectNextMatches(chatMessage -> chatMessage.getText().equals("helo") &&
                        chatMessage.getUserNickName().equals("abd") &&
                        chatMessage.getChatId().equals("ccc"));
    }
}
