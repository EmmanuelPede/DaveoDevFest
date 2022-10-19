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
        return new ArrayList<>(this.repository.findAllByOrderByBestScoreDesc());
    }

    @PostMapping("/customer")
    public Customer postCustomer(@RequestBody final Customer customer) {
        log.info("Saving new customer {}", customer);
        return this.repository
                .save(new Customer(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getLastScore(),
                        customer.getBestScore() != null ? customer.getBestScore() : customer.getLastScore(),
                        customer.getActive() != null ? customer.getActive() : true,
                        customer.getCreated() != null ? customer.getCreated() : new Date(),
                        customer.getLastRideDate(),
                        customer.getLastSelectDate() != null ? customer.getLastSelectDate() : new Date(),
                        customer.getRideIdList(),
                        customer.getVCard()
                ));
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") final String id) {
        this.repository.deleteById(id);
        return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
    }

    @GetMapping("customers/name/{name}")
    public List<Customer> findByFirstName(@PathVariable final String name) {
        return this.repository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("customers/email/{email}")
    public List<Customer> findByEmail(@PathVariable final String email) {
        return this.repository.findByEmailContainingIgnoreCase(email);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") final String id, @RequestBody final Customer updatedCustomer) {
        log.info("Update Customer with ID = " + id + "...");

        final Optional<Customer> customerData = this.repository.findById(id);

        if (customerData.isPresent()) {

            final Customer customerToUpdate = customerData.get();

            if (updatedCustomer.getName() != null) {
                customerToUpdate.setName(updatedCustomer.getName());
            }

            if (updatedCustomer.getEmail() != null) {
                customerToUpdate.setEmail(updatedCustomer.getEmail());
            }

            if (updatedCustomer.getActive() != null) {
                customerToUpdate.setActive(updatedCustomer.getActive());
            }

            if (updatedCustomer.getBestScore() != null) {
                customerToUpdate.setBestScore(updatedCustomer.getBestScore());
            }

            if (updatedCustomer.getLastScore() != null) {
                customerToUpdate.setLastScore(updatedCustomer.getLastScore());

                if (customerToUpdate.getBestScore() == null || customerToUpdate.getBestScore() < updatedCustomer.getLastScore()) {
                    customerToUpdate.setBestScore(updatedCustomer.getLastScore());
                }
            }

            if (updatedCustomer.getLastRideDate() != null) {
                customerToUpdate.setLastRideDate(updatedCustomer.getLastRideDate());
            }

            if (updatedCustomer.getLastSelectDate() != null) {
                customerToUpdate.setLastSelectDate(updatedCustomer.getLastSelectDate());
            }

            if (updatedCustomer.getRideIdList() != null) {
                customerToUpdate.setRideIdList(updatedCustomer.getRideIdList());
            }

            if (updatedCustomer.getVCard() != null) {
                customerToUpdate.setVCard(updatedCustomer.getVCard());
            }
            return new ResponseEntity<>(this.repository.save(customerToUpdate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
