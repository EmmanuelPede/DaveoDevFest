package com.daveo.spring.restapi.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.repo.CustomerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository repository;

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		repository.findAll().forEach(customers::add);
		return customers;
	}

	@PostMapping("/customer")
	public Customer postCustomer(@RequestBody Customer customer) {
		Customer saveCustomer = repository.save(new Customer(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getScore()));
		return saveCustomer;
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
		repository.deleteById(id);
		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}

	@GetMapping("customers/firstname/{firstname}")
	public List<Customer> findByFirstName(@PathVariable String firstName) {
		List<Customer> customers = repository.findByFirstName(firstName);
		return customers;
	}

	@GetMapping("customers/email/{email}")
	public List<Customer> findByEmail(@PathVariable String email) {
		List<Customer> customers = repository.findByEmail(email);
		return customers;
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
		System.out.println("Update Customer with ID = " + id + "...");

		Optional<Customer> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			Customer updateCustomer = customerData.get();
			updateCustomer.setFirstName(customer.getFirstName());
			updateCustomer.setLastName(customer.getLastName());
			updateCustomer.setEmail(customer.getEmail());
			updateCustomer.setScore(customer.getScore());
			updateCustomer.setActive(customer.isActive());
			return new ResponseEntity<>(repository.save(updateCustomer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}