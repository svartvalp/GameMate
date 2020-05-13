package com.svartvalp.GameMate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Collection;
import java.util.List;


@Configuration
@EnableWebFlux
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.svartvalp.GameMate"})
public class AppConfig extends AbstractReactiveMongoConfiguration {

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
