package com.svartvalp.GameMate.Security;

import com.svartvalp.GameMate.Exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;


//@Component
public class ChatMessagesPublicKeyFilter implements WebFilter {
    PublicKeyUtils publicKeyUtils;

    @Autowired
    public void setPublicKeyUtils(PublicKeyUtils publicKeyUtils) {
        this.publicKeyUtils = publicKeyUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        if(serverWebExchange.getRequest().getURI().getPath().contains("/messages/chat/")) {
            var chatKeyList = serverWebExchange.getRequest().getHeaders().get("publicChatKey");
            if(chatKeyList != null && chatKeyList.size() > 0) {
                String publicKey = chatKeyList.get(0);
                var subjects = publicKeyUtils.verifyKeyAndReturnSubject(publicKey).split(".");
                String chatId = subjects[0];
                String userNickname = subjects[1];
                if(chatId != null && userNickname != null) {
                    return webFilterChain.filter(serverWebExchange);
                }
            }
        }
        var response = serverWebExchange.getResponse();
        DataBuffer buffer = response.bufferFactory().allocateBuffer();
        buffer.write("public key not provided or not valid", StandardCharsets.UTF_8);
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.writeAndFlushWith(Flux.just(Flux.just(buffer)));
    }
}
