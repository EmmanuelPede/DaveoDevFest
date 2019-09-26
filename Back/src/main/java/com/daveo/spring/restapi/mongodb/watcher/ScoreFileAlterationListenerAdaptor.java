package com.daveo.spring.restapi.mongodb.watcher;

import java.io.BufferedReader;
import java.io.File;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.daveo.spring.restapi.mongodb.parser.RideDto;
import com.daveo.spring.restapi.mongodb.parser.ScoreParser;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ScoreFileAlterationListenerAdaptor implements FileAlterationListener {

    private final String outputFileName;

    private final ScoreParser scoreParser;

    private final ApplicationEventPublisher eventPublisher;

    private final BufferedReader reader;

    @Autowired
    public ScoreFileAlterationListenerAdaptor(final ScoreParser scoreParser,
            final ApplicationEventPublisher eventPublisher,
            final BufferedReader bufferedReader,
            @Value("${watcher.audiosurf.output.filename}") final String outputFileName) {
        this.scoreParser = scoreParser;
        this.eventPublisher = eventPublisher;
        this.reader = bufferedReader;

        this.outputFileName = outputFileName;
    }

    @Override public void onStart(final FileAlterationObserver fileAlterationObserver) {
    }

    @Override public void onDirectoryCreate(final File file) {

    }

    @Override public void onDirectoryChange(final File file) {

    }

    @Override public void onDirectoryDelete(final File file) {

    }

    @Override public void onFileCreate(final File file) {
        log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileCreate", file);
    }

    @Override public void onFileChange(final File file) {
        // code for processing change event
        log.debug("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileChange", file);

        if (file == null || !file.getName().equals(outputFileName)) {
            log.warn("[AS2-TRACKER] other file has been updated during watch: {}.", file);
        } else {
            if (file.exists()) {
                final RideDto lastRide = scoreParser.handleFile(reader);

                if (lastRide != null) {
                    log.info("[AS2-TRACKER] Publishing Last ride : {}.", lastRide);

                    // Publish event of type RideDto
                    eventPublisher.publishEvent(lastRide);

                } else {
                    log.info("[AS2-TRACKER] No new score.");
                }
            }
        }
    }

    @Override public void onFileDelete(final File file) {
        log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileDelete", file);
    }

    @Override public void onStop(final FileAlterationObserver fileAlterationObserver) {

    }
}
