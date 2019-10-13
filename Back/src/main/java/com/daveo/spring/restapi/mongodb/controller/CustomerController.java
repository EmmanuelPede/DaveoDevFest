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
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") final String id, @RequestBody final Customer updatedcustomer) {
        log.info("Update Customer with ID = " + id + "...");

        final Optional<Customer> customerData = this.repository.findById(id);

        if (customerData.isPresent()) {

            final Customer updateCustomer = customerData.get();

            if (updatedcustomer.getName() != null) {
                updatedcustomer.setName(updatedcustomer.getName());
            }

            if (updatedcustomer.getEmail() != null) {
                updatedcustomer.setEmail(updatedcustomer.getEmail());
            }

            if (updatedcustomer.getActive() != null) {
                updatedcustomer.setActive(updatedcustomer.getActive());
            }

            if (updatedcustomer.getBestScore() != null) {
                updatedcustomer.setBestScore(updatedcustomer.getBestScore());
            }

            if (updatedcustomer.getLastScore() != null) {
                updatedcustomer.setLastScore(updatedcustomer.getLastScore());

                if (updatedcustomer.getBestScore() == null || updatedcustomer.getBestScore() < updatedcustomer.getLastScore()) {
                    updatedcustomer.setBestScore(updatedcustomer.getLastScore());
                }
            }

            if (updatedcustomer.getLastRideDate() != null) {
                updatedcustomer.setLastRideDate(updatedcustomer.getLastRideDate());
            }

            if (updatedcustomer.getRideIdList() != null) {
                updatedcustomer.setRideIdList(updatedcustomer.getRideIdList());
            }

            if (updatedcustomer.getVCard() != null) {
                updatedcustomer.setVCard(updatedcustomer.getVCard());
            }
            return new ResponseEntity<>(this.repository.save(updatedcustomer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
