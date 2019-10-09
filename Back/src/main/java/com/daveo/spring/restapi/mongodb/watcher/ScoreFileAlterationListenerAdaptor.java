package com.daveo.spring.restapi.mongodb.watcher;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.model.Ride;
import com.daveo.spring.restapi.mongodb.parser.ScoreParser;
import com.daveo.spring.restapi.mongodb.repo.CustomerRepository;
import com.daveo.spring.restapi.mongodb.repo.RideRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Log4j2
@Component
public class ScoreFileAlterationListenerAdaptor implements FileAlterationListener {

    private final String outputFileName;

    private final ScoreParser scoreParser;

    private final ApplicationEventPublisher eventPublisher;

    private final BufferedReader reader;

    private final CustomerRepository customerRepository;

    private final RideRepository rideRepository;

    @Autowired
    public ScoreFileAlterationListenerAdaptor(final ScoreParser scoreParser,
                                              final ApplicationEventPublisher eventPublisher,
                                              final BufferedReader bufferedReader,
                                              @Value("${watcher.audiosurf.output.filename}") final String outputFileName, final CustomerRepository customerRepository, final RideRepository rideRepository) {
        this.scoreParser = scoreParser;
        this.eventPublisher = eventPublisher;
        this.reader = bufferedReader;
        this.outputFileName = outputFileName;
        this.customerRepository = customerRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public void onStart(final FileAlterationObserver fileAlterationObserver) {
    }

    @Override
    public void onDirectoryCreate(final File file) {

    }

    @Override
    public void onDirectoryChange(final File file) {

    }

    @Override
    public void onDirectoryDelete(final File file) {

    }

    @Override
    public void onFileCreate(final File file) {
        log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileCreate", file);
    }

    @Override
    public void onFileChange(final File file) {
        // code for processing change event
        log.debug("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileChange", file);

        try {
            if (file == null || !file.getName().equals(this.outputFileName)) {
                log.warn("[AS2-TRACKER] other file has been updated during watch: {}.", file);
            } else {
                if (file.exists()) {
                    final long nbLineOfFile = Files.lines(Paths.get(file.getPath())).count();
                    final RideDto lastRide = this.scoreParser.handleFile(this.reader, nbLineOfFile);

                    if (lastRide != null) {
                        this.saveLastRide(lastRide);

                        log.info("[AS2-TRACKER] Publishing Last ride : {}.", lastRide);

                        // Publish event of type RideDto
                        this.eventPublisher.publishEvent(lastRide);

                    } else {
                        log.info("[AS2-TRACKER] No new score.");
                    }
                }
            }
        } catch (final Exception e) {
            log.error("Error during score parsing", e);
        }
    }

    private void saveLastRide(final RideDto lastRide) {
        log.info("[AS2-TRACKER] Saving Last ride : {}.", lastRide);

        // Get current customer
        final Customer customer = this.customerRepository.findFirstByActiveTrueOrderByCreatedDesc();
        if (customer != null) {

            lastRide.setCustomerId(customer.getId());

            final Optional<Ride> optRide = this.rideRepository.findByKey(lastRide.getKey());
            if (optRide.isPresent()) {
                final Ride ride = optRide.get();
                if (customer.getRideIdList() == null) {
                    customer.setRideIdList(new HashSet<>());
                }
                customer.setLastRideDate(new Date());
                customer.setLastScore(lastRide.getScore());

                ride.setCustomerId(customer.getId());
                customer.getRideIdList().add(ride.getId());

                if (customer.getBestScore() == null || customer.getBestScore() < lastRide.getScore()) {
                    customer.setBestScore(lastRide.getScore());
                }

                this.customerRepository.save(customer);
                this.rideRepository.save(ride);
            }
        }
    }

    @Override
    public void onFileDelete(final File file) {
        log.info("[AS2-TRACKER] Event kind: {}. File affected: {}.", "onFileDelete", file);
    }

    @Override
    public void onStop(final FileAlterationObserver fileAlterationObserver) {

    }
}
