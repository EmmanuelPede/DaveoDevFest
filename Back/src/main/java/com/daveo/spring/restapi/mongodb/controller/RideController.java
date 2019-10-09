package com.daveo.spring.restapi.mongodb.controller;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.mapper.RideMapper;
import com.daveo.spring.restapi.mongodb.repo.RideRepository;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CustomerController
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RideController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    public RideController(final RideRepository rideRepository, final RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.rideMapper = rideMapper;
    }

    @GetMapping("/last-ride")
    public RideDto getLastRide() {
        return this.rideMapper.toRideDto(this.rideRepository.findFirstByOrderByCreatedDesc());
    }


    @GetMapping(path = "/score", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handleScore(final HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");

        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;
    }

    @EventListener
    public void onNewRideInfo(final RideDto rideDto) {
        final List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {

                emitter.send(rideDto);

                // close connnection, browser automatically reconnects
                // emitter.complete();

            } catch (final Exception e) {
                deadEmitters.add(emitter);
            }
        });

        this.emitters.removeAll(deadEmitters);
    }
}
