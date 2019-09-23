package com.daveo.spring.restapi.mongodb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private int lastScore;
    private int bestScore;
    private boolean active;
    private Date created;
    private Date lastRideDate;

    private List<Ride> rideList;

    public Customer() {
    }

    public Customer(String firstName, int lastScore) {
        this.firstName = firstName;
        this.lastScore = lastScore;
    }

    public Customer(final String firstName, final String lastName, final String email, final int lastScore) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.lastScore = lastScore;
    }

	@Override
	public String toString() {
		return "Customer{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", lastScore=" + lastScore +
				", bestScore=" + bestScore +
				", active=" + active +
				", created=" + created +
				", lastRideDate=" + lastRideDate +
				", rideList=" + rideList +
				'}';
	}
}
