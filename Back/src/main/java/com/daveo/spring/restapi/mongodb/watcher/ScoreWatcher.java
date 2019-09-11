package com.daveo.spring.restapi.mongodb.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.daveo.spring.restapi.mongodb.parser.ScoreParser;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ScoreWatcher {

    @Value("${watcher.audiosurf.output.path:}")
    private String outputPathDir;

    @Value("${watcher.audiosurf.output.filename}")
    private String outputFileName;

    @Autowired
    private ScoreParser scoreParser;

    @Autowired
    ResourceLoader resourceLoader;

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void watch() {
        try {
            final WatchService watchService = FileSystems.getDefault().newWatchService();

            final String pathString = System.getProperty("user.home") + outputPathDir;
            log.info("[AS2-TRACKER] path string of output file : {}", pathString);

            final Path path = Paths.get(pathString);
            log.info("[AS2-TRACKER] path of output file : {}", path);

            final File directory = new File(pathString);
            if (!directory.exists()) {
                directory.mkdir();
            }

            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                List<WatchEvent<?>> watchEvents = key.pollEvents();
                for (WatchEvent<?> event : watchEvents) {
                    log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", event.kind(), event.context());

                    if (event.context() == null || !event.context().toString().equals(outputFileName)) {
                        log.warn("[AS2-TRACKER] other file has been updated during watch: {}.", event.context());
                    } else {

                        final File file = new File(pathString + outputFileName);
                        if (file.exists()) {
                            final Long lastScore = scoreParser.handleFile(file);
                            log.info("[AS2-TRACKER] Last score {}.", lastScore);

                        }
                    }
                }
                key.reset();
            }
        } catch (InterruptedException | IOException e) {
            log.error("[AS2-TRACKER] Erreur lors de l'exécution du watcher.", e);
        }
    }
}
