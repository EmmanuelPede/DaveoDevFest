package com.daveo.spring.restapi.mongodb.mapper;

import com.daveo.spring.restapi.mongodb.dto.CustomerDto;
import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.dto.SongDto;
import com.daveo.spring.restapi.mongodb.model.Customer;
import com.daveo.spring.restapi.mongodb.model.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RideMapper {

    final CustomerMapper customerMapper;

    @Autowired
    public RideMapper(final CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public RideDto toRideDto(final Ride ride) {
        if (ride == null) {
            return null;
        }

        final RideDto rideDto = this.convertToRideDto(ride);

        final CustomerDto customerDto = new CustomerDto();
        customerDto.setId(ride.getCustomerId());
        rideDto.setCustomer(customerDto);

        return rideDto;
    }

    public RideDto toRideDto(final Ride ride, final Function<String, Customer> customerFonction) {
        if (ride == null) {
            return null;
        }

        final RideDto rideDto = this.convertToRideDto(ride);

        if (customerFonction != null && ride.getCustomerId() != null) {
            rideDto.setCustomer(this.customerMapper.toCustomerDto(customerFonction.apply(ride.getCustomerId())));
        }

        return rideDto;
    }

    private RideDto convertToRideDto(final Ride ride) {
        final RideDto rideDto = new RideDto();
        rideDto.setKey(ride.getKey());
        rideDto.setScore(ride.getScore());

        final SongDto songDto = new SongDto();

        songDto.setSongArtist(ride.getSongArtist());
        songDto.setSongDuration(ride.getSongDuration());
        songDto.setSongName(ride.getSongName());

        rideDto.setSong(songDto);
        return rideDto;
    }
}
