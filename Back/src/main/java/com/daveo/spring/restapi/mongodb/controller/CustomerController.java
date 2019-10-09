package com.daveo.spring.restapi.mongodb.controller;

import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.repo.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * CustomerController
 */
@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(final CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(this.repository.findAll());
    }

    @PostMapping("/customer")
    public Customer postCustomer(@RequestBody final Customer customer) {
        return this.repository
                .save(new Customer(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getLastScore(),
                        customer.getBestScore(),
                        customer.getActive() != null ? customer.getActive() : true,
                        customer.getCreated() != null ? customer.getCreated() : new Date(),
                        customer.getLastRideDate(),
                        customer.getRideIdList()
                ));
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") final String id) {
        this.repository.deleteById(id);
        return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
    }

    @GetMapping("customers/firstname/{firstname}")
    public List<Customer> findByFirstName(@PathVariable final String firstName) {
        return this.repository.findByFirstNameContainingIgnoreCase(firstName);
    }

    @GetMapping("customers/email/{email}")
    public List<Customer> findByEmail(@PathVariable final String email) {
        return this.repository.findByEmailContainingIgnoreCase(email);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") final String id, @RequestBody final Customer customer) {
        log.info("Update Customer with ID = " + id + "...");

        final Optional<Customer> customerData = this.repository.findById(id);

        if (customerData.isPresent()) {
            final Customer updateCustomer = customerData.get();
            if (customer.getFirstName() != null) {
                updateCustomer.setFirstName(customer.getFirstName());
            }

            if (customer.getLastName() != null) {
                updateCustomer.setLastName(customer.getLastName());
            }

            if (customer.getEmail() != null) {
                updateCustomer.setEmail(customer.getEmail());
            }

            if (customer.getLastScore() != null) {
                updateCustomer.setLastScore(customer.getLastScore());
            }

            if (customer.getActive() != null) {
                updateCustomer.setActive(customer.getActive());
            }

            if (customer.getBestScore() != null) {
                updateCustomer.setBestScore(customer.getBestScore());
            }

            if (customer.getLastRideDate() != null) {
                updateCustomer.setLastRideDate(customer.getLastRideDate());
            }

            if (customer.getRideIdList() != null) {
                updateCustomer.setRideIdList(customer.getRideIdList());
            }
            return new ResponseEntity<>(this.repository.save(updateCustomer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
