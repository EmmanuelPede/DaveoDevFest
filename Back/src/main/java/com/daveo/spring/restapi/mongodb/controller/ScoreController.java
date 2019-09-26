package com.daveo.spring.restapi.mongodb.controller;

import com.daveo.spring.restapi.mongodb.parser.RideDto;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ScoreController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(path = "/score", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handleScore(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        // SseEmitter emitter = new SseEmitter(180_000L);

        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;
    }

    @EventListener
    public void onRideInfo(final RideDto rideDto) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                emitter.send(rideDto);

                // close connnection, browser automatically reconnects
                // emitter.complete();

                // SseEventBuilder builder = SseEmitter.event().name("second").data("1");
                // SseEventBuilder builder =
                // SseEmitter.event().reconnectTime(10_000L).data(memoryInfo).id("1");
                // emitter.send(builder);
            }
            catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });

        this.emitters.removeAll(deadEmitters);
    }
}
