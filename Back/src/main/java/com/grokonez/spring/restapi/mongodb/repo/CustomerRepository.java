package com.grokonez.spring.restapi.mongodb.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.grokonez.spring.restapi.mongodb.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	List<Customer> findByAge(int age);
}
