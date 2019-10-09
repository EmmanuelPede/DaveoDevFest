package com.daveo.spring.restapi.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableScheduling
@EnableWebFlux
@SpringBootApplication
public class BackApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }
}
