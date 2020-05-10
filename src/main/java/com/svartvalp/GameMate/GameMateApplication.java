package com.svartvalp.GameMate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class GameMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameMateApplication.class, args);
	}

}
