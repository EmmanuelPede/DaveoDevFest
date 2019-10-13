package com.daveo.spring.restapi.mongodb.mapper;

import com.daveo.spring.restapi.mongodb.dto.CustomerDto;
import com.daveo.spring.restapi.mongodb.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toCustomerDto(final Customer customer) {
        if (customer == null) {
            return null;
        }

        final CustomerDto customerDto = new CustomerDto();

        customerDto.setId(customer.getId());

        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setLastScore(customer.getLastScore());
        customerDto.setBestScore(customer.getBestScore());
        customerDto.setActive(customer.getActive());
        customerDto.setCreated(customer.getCreated());
        customerDto.setLastRideDate(customer.getLastRideDate());
        customerDto.setVCard(customer.getVCard());

        return customerDto;
    }
}
