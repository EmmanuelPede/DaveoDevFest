package com.daveo.spring.restapi.mongodb.watcher;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j2
public class ScoreWatcher {

    private static final int POLL_INTERVAL = 500;

    private final ScoreFileAlterationListenerAdaptor scoreFileAlterationListenerAdaptor;

    private final String outputPathDir;

    @Autowired
    public ScoreWatcher(final ScoreFileAlterationListenerAdaptor scoreFileAlterationListenerAdaptor,
                        @Value("${watcher.audiosurf.output.path:}") final String outputPathDir) {
        this.scoreFileAlterationListenerAdaptor = scoreFileAlterationListenerAdaptor;
        this.outputPathDir = outputPathDir;
    }

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void watch() {

        final String pathString = System.getProperty("user.home") + this.outputPathDir;
        log.info("[AS2-TRACKER] path string of output file : {}", pathString);

        final File directory = new File(pathString);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            final FileAlterationObserver observer = new FileAlterationObserver(pathString);
            final FileAlterationMonitor monitor = new FileAlterationMonitor(POLL_INTERVAL);
            observer.addListener(this.scoreFileAlterationListenerAdaptor);
            monitor.addObserver(observer);

            monitor.start();

        } catch (final Exception e) {
            log.error("[AS2-TRACKER] Erreur lors de l'exécution du watcher.", e);
        }
    }
}
