package com.daveo.spring.restapi.mongodb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerDto {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Long lastScore;

    private Long bestScore;

    private Boolean active;

    private Date created;

    private Date lastRideDate;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id='" + this.id + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", email='" + this.email + '\'' +
                ", lastScore=" + this.lastScore +
                ", bestScore=" + this.bestScore +
                ", active=" + this.active +
                ", created=" + this.created +
                ", lastRideDate=" + this.lastRideDate +
                '}';
    }
}
