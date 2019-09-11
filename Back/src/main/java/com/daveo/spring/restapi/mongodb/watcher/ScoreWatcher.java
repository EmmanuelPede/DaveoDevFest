package com.daveo.spring.restapi.mongodb.watcher;

import com.daveo.spring.restapi.mongodb.parser.ScoreParser;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

@Component
@Log4j2
public class ScoreWatcher {

    @Value("${watcher.audiosurf.output.path}")
    private String outputPathDir;

    @Value("${watcher.audiosurf.output.filename}")
    private String outputFileName;

    @Autowired
    private ScoreParser scoreParser;

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void watch() {
        try {
            final WatchService watchService = FileSystems.getDefault().newWatchService();

            final String pathString = outputPathDir;
            log.info("[AS2-TRACKER] path string of output file : {}", pathString);

            final Path path = Paths.get(pathString);
            log.info("[AS2-TRACKER] path of output file : {}", path);

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", event.kind(), event.context());

                    if (event.context() == null || !event.context().toString().equals(outputFileName)) {
                        log.warn("[AS2-TRACKER] other file has been updated during watch: {}.", event.context());

                    } else {
                        final File file = new File(pathString + outputFileName);
                        log.info("[AS2-TRACKER] File: {}.", file);

                        final String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        log.info("[AS2-TRACKER] FileContentLength: {}.", fileContent.length());

                        final String[] lines = fileContent.split("\\r?\\n");

                        Stream.of(lines).forEach(l -> scoreParser.handleLine(l));
                        scoreParser.handleLine(fileContent);
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            log.error("[AS2-TRACKER] Erreur lors de l'exécution du watcher.", e);
        }
    }
}
