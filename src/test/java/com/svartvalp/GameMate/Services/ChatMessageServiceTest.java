package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.AppConfig;
import com.svartvalp.GameMate.Controllers.ChatMessageController;
import com.svartvalp.GameMate.Models.ChatMessage;
import com.svartvalp.GameMate.Repositories.ChatMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
public class ChatMessageServiceTest {
    @MockBean
    ChatMessageRepository chatMessageRepository;



    @Test
    public void testFindAllMessagesByChatId() {
        Mockito.when(chatMessageRepository.findAllByChatId("1")).thenReturn(
                Flux.just(new ChatMessage("dad", 111, "aaa", "ccc"))
        );
        IChatMessageService chatMessageService = new ChatMessageService(chatMessageRepository);
        StepVerifier.create( chatMessageService.findAllMessagesByChatId("1"))
                .expectNextMatches(chatMessage -> chatMessage.getText().equals("dad"));
    }
}
