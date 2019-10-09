package com.daveo.spring.restapi.mongodb.repo;

import com.daveo.spring.restapi.mongodb.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    Customer findFirstByActiveTrueOrderByCreatedDesc();
}
