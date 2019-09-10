package com.daveo.spring.restapi.mongodb.repo;

import java.util.List;

import com.daveo.spring.restapi.mongodb.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	List<Customer> findByFirstName(String firstName);
	List<Customer> findByEmail(String email);
}
