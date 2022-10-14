package com.daveo.spring.restapi.mongodb.controller;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.dto.ScoreDto;
import com.daveo.spring.restapi.mongodb.mapper.RideMapper;
import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.model.Ride;
import com.daveo.spring.restapi.mongodb.repo.CustomerRepository;
import com.daveo.spring.restapi.mongodb.repo.RideRepository;
import com.daveo.spring.restapi.mongodb.service.RideService;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * CustomerController
 */
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class RideController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    private final RideRepository repo;

    private final CustomerRepository customerRepo;

    private final RideMapper rideMapper;

    private final RideService rideService;

    public RideController(final RideRepository repo, final CustomerRepository customerRepo, final RideMapper rideMapper, RideService rideService) {
        this.repo = repo;
        this.customerRepo = customerRepo;
        this.rideMapper = rideMapper;
        this.rideService = rideService;
    }

    @GetMapping("/last-ride")
    public RideDto getLastRide() {
        final Ride ride = this.repo.findFirstByOrderByCreatedDesc();

        Function<String, Customer> customerFunction = null;
        if (ride != null && ride.getCustomerId() != null) {
            customerFunction = id -> this.customerRepo.findById(id).orElse(null);
        }
        return rideMapper.toRideDto(ride, customerFunction);
    }

    @PostMapping("/score")
    public void saveLastScore(@RequestBody final ScoreDto scoreDto){
        rideService.saveLastRideAndPublish(scoreDto.getScore());
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

                // close connection, browser automatically reconnects
                emitter.complete();

            } catch (final Exception e) {
                deadEmitters.add(emitter);
            }
        });

        this.emitters.removeAll(deadEmitters);
    }
}
