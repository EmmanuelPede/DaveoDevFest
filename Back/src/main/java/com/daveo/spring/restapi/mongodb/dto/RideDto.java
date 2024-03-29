package com.daveo.spring.restapi.mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ride = sondDto + score
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long score;

    private Long key;

    private CustomerDto customer;

    @Override
    public String toString() {
        return "RideDto{" +
                "score=" + score +
                ", key=" + key +
                ", customer=" + customer +
                '}';
    }
}
