package com.daveo.spring.restapi.mongodb.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final String outputPathDir;

    private final String outputFileName;

    public AppConfig(@Value("${watcher.audiosurf.output.path:}") final String outputPathDir,
            @Value("${watcher.audiosurf.output.filename}") final String outputFileName) {
        this.outputPathDir = outputPathDir;
        this.outputFileName = outputFileName;
    }

    @Bean
    public BufferedReader bufferedReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(System.getProperty("user.home") + outputPathDir + outputFileName));
    }
}
