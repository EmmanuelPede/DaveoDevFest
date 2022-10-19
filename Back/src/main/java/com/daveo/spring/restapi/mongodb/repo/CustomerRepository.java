package com.daveo.spring.restapi.mongodb.repo;

import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.repo.custom.CustomCustomerRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String>, CustomCustomerRepository {
    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    Customer findFirstByActiveTrueOrderByLastSelectDateDesc();

    List<Customer> findAllByOrderByBestScoreDesc();
}
