package com.daveo.spring.restapi.mongodb.repo;

import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.repo.custom.CustomCustomerRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String>, CustomCustomerRepository {
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    Customer findFirstByActiveTrueOrderByCreatedDesc();

    List<Customer> findAllByOrderByBestScoreDesc();
}
