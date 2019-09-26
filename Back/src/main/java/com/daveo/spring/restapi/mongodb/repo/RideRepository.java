package com.daveo.spring.restapi.mongodb.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.daveo.spring.restapi.mongodb.model.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {
}
