package com.daveo.spring.restapi.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Long lastScore;

    private Long bestScore;

    private Boolean active;

    private Date created;

    private Date lastRideDate;

    private List<Ride> rideList;

    public Customer(final String firstName, final Long lastScore) {
        this.firstName = firstName;
        this.lastScore = lastScore;
    }

    public Customer(final String firstName, final String lastName, final String email, final Long lastScore) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.lastScore = lastScore;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + this.id + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", email='" + this.email + '\'' +
                ", lastScore=" + this.lastScore +
                ", bestScore=" + this.bestScore +
                ", active=" + this.active +
                ", created=" + this.created +
                ", lastRideDate=" + this.lastRideDate +
                ", rideList=" + this.rideList +
                '}';
    }
}
