package com.daveo.spring.restapi.mongodb.watcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import com.daveo.spring.restapi.mongodb.parser.RideDto;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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

    private final ScoreParser scoreParser;

    private final ApplicationEventPublisher eventPublisher;

    private static final int POLL_INTERVAL = 500;

    public ScoreWatcher(ScoreParser scoreParser, ApplicationEventPublisher eventPublisher) {
        this.scoreParser = scoreParser;
        this.eventPublisher = eventPublisher;
    }

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

            FileAlterationObserver observer = new FileAlterationObserver(pathString);
            FileAlterationMonitor monitor = new FileAlterationMonitor(POLL_INTERVAL);
            FileAlterationListener listener = new FileAlterationListenerAdaptor() {
                @Override
                public void onFileCreate(File file) {
                    log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileCreate", file);
                }

                @Override
                public void onFileDelete(File file) {
                    log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileDelete", file);

                }

                @Override
                public void onFileChange(File file) {
                    // code for processing change event
                    log.debug("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileChange", file);

                    if (file == null || !file.getName().equals(outputFileName)) {
                        log.warn("[AS2-TRACKER] other file has been updated during watch: {}.", file);
                    } else {
                        if (file.exists()) {
                            final RideDto lastRide = scoreParser.handleFile(file);
                            log.info("[AS2-TRACKER] Last score {}.", lastRide);

                            if (lastRide != null) {
                                eventPublisher.publishEvent(lastRide);
                            }
                        }
                    }
                }
            };
            observer.addListener(listener);
            monitor.addObserver(observer);

            monitor.start();

//            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

 /*           WatchKey key;
            while ((key = watchService.take()) != null) {
                List<WatchEvent<?>> watchEvents = key.pollEvents();
                for (WatchEvent<?> event : watchEvents) {
                    log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", event.kind(), event.context());

                    if (event.context() == null || !event.context().toString().equals(outputFileName)) {
                        log.warn("[AS2-TRACKER] other file has been updated during watch: {}.", event.context());
                    } else {

                        final File file = new File(pathString + outputFileName);
                        if (file.exists()) {
                            final RideDto lastRide = scoreParser.handleFile(file);
                            log.info("[AS2-TRACKER] Last score {}.", lastRide);

                            if (lastRide != null) {
                                this.eventPublisher.publishEvent(lastRide);
                            }
                        }
                    }
                }
                key.reset();
            }*/
        } catch (InterruptedException | IOException e) {
            log.error("[AS2-TRACKER] Erreur lors de l'exécution du watcher.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
