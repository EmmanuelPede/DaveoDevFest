package com.daveo.spring.restapi.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
	@Id
	private String id;

	private String firstName;
	private String lastName;
	private String email;
	private int score;
	private boolean active;

	public Customer() {
	}

	public Customer(String firstName, int score) {
		this.firstName = firstName;
		this.score = score;
	}

	public Customer(final String firstName, final String lastName, final String email, final int score) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() { return lastName; }

	public void setLastName(final String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }

	public void setEmail(final String email) { this.email = email; }

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", score=" + score + ", active=" + active + "]";
	}
}
