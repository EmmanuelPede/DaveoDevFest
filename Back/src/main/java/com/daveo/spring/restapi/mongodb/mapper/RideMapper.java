package com.daveo.spring.restapi.mongodb.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.model.Ride;

@Component
public class RideMapper {

    final CustomerMapper customerMapper;

    @Autowired
    public RideMapper(final CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public RideDto toRideDto(final Ride ride, final Function<String, Customer> customerFunction) {
        if (ride == null) {
            return null;
        }

        final RideDto rideDto = this.convertToRideDto(ride);

        if (customerFunction != null && ride.getCustomerId() != null) {
            rideDto.setCustomer(this.customerMapper.toCustomerDto(customerFunction.apply(ride.getCustomerId())));
        }

        return rideDto;
    }

    private RideDto convertToRideDto(final Ride ride) {
        final RideDto rideDto = new RideDto();
        rideDto.setKey(ride.getKey());
        rideDto.setScore(ride.getScore());

        return rideDto;
    }
}
