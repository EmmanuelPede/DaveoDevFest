package com.daveo.spring.restapi.mongodb.repo;

import com.daveo.spring.restapi.mongodb.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RideRepository extends MongoRepository<Ride, String> {

    Optional<Ride> findByKey(Long key);

    Ride findFirstByOrderByCreatedDesc();
}
