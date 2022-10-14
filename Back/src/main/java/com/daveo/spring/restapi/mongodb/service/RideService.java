package com.daveo.spring.restapi.mongodb.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.mapper.RideMapper;
import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.model.Ride;
import com.daveo.spring.restapi.mongodb.repo.CustomerRepository;
import com.daveo.spring.restapi.mongodb.repo.RideRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RideService {

    private final CustomerRepository customerRepository;

    private final RideRepository rideRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final RideMapper rideMapper;


    @Autowired
    public RideService(CustomerRepository customerRepository, RideRepository rideRepository, ApplicationEventPublisher eventPublisher, RideMapper rideMapper) {
        this.customerRepository = customerRepository;
        this.rideRepository = rideRepository;
        this.eventPublisher = eventPublisher;
        this.rideMapper = rideMapper;
    }

    public void saveLastRideAndPublish(final Long score) {
        log.info("Saving Last score : {}.", score);

        final Ride lastRide = new Ride();

        lastRide.setScore(score);
        lastRide.setKey(new Random().nextLong());

        // Get current customer
        final Customer customer = this.customerRepository.findFirstByActiveTrueOrderByCreatedDesc();
        if (customer != null) {

            lastRide.setCustomerId(customer.getId());

            if (customer.getRideIdList() == null) {
                customer.setRideIdList(new HashSet<>());
            }
            customer.setLastRideDate(new Date());
            customer.setLastScore(score);

            customer.getRideIdList().add(lastRide.getId());

            if (customer.getBestScore() == null || customer.getBestScore() < lastRide.getScore()) {
                customer.setBestScore(lastRide.getScore());
            }

            this.customerRepository.save(customer);
            this.rideRepository.save(lastRide);

            final Function<String, Customer> customerFunction = id -> this.customerRepository.findById(id).orElse(null);

            final RideDto lastRideDto = rideMapper.toRideDto(lastRide, customerFunction);

            log.info("Publishing Last ride : {}.", lastRideDto);

            this.eventPublisher.publishEvent(lastRideDto);

            log.info("Last ride published : {}.", lastRideDto);
        }
    }
}
