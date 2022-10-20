package com.daveo.spring.restapi.mongodb.repo;

import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.repo.custom.CustomCustomerRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String>, CustomCustomerRepository {
    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    Optional<Customer> findFirstByVCardIgnoreCaseOrderByLastSelectDateDesc(String vCard);

    Optional<Customer> findFirstByEmailIgnoreCaseAndNameIgnoreCaseOrderByLastSelectDateDesc(String email, String name);

    Customer findFirstByActiveTrueOrderByLastSelectDateDesc();

    List<Customer> findAllByOrderByBestScoreDesc();
}
