package com.svartvalp.GameMate;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.svartvalp.GameMate.Controllers.WebSocketMessagesHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebFlux
@EnableScheduling
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.svartvalp.GameMate"})
public class AppConfig extends AbstractReactiveMongoConfiguration {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Bean
    public HandlerAdapter socketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping webSocketMapping() {
        Map<String, WebSocketHandler> handlerMap = new HashMap<>();
        handlerMap.put("/events/{chatId}", webSocketHandler);
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(1);
        simpleUrlHandlerMapping.setUrlMap(handlerMap);
        return simpleUrlHandlerMapping;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Value("${mongo.client.connection_uri}")
    private String connectionURI;

    @Bean
    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(connectionURI);
    }


    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return List.of("com.svartvalp.GameMate.Repositories");
    }
}
