package com.daveo.spring.restapi.mongodb.config;

import java.io.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class AppConfig {

    private final String outputPathDir;

    private final String outputFileName;

    public AppConfig(@Value("${watcher.audiosurf.output.path:}") final String outputPathDir,
        @Value("${watcher.audiosurf.output.filename}") final String outputFileName) {
        this.outputPathDir = outputPathDir;
        this.outputFileName = outputFileName;
    }

    @Bean
    public BufferedReader bufferedReader() throws IOException {
        final String ouputDirPathString = System.getProperty("user.home") + this.outputPathDir;
        log.info("[AS2-TRACKER] creating bufferedReader Bean");

        final File outputFile = new File(ouputDirPathString + outputFileName);
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();
        }

        return new BufferedReader(new FileReader(outputFile));
    }
}
