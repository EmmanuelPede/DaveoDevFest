package com.daveo.spring.restapi.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;

    private String name;

    private String email;

    private Long lastScore;

    private Long bestScore;

    private Boolean active;

    private Date created;

    private Date lastRideDate;

    private Set<String> rideIdList;

    private String vCard;

    public Customer(final String name, final Long lastScore) {
        this.name = name;
        this.lastScore = lastScore;
    }

    public Customer(final String name, final String email, final Long lastScore) {
        this.name = name;
        this.email = email;
        this.lastScore = lastScore;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                ", lastScore=" + this.lastScore +
                ", bestScore=" + this.bestScore +
                ", active=" + this.active +
                ", created=" + this.created +
                ", lastRideDate=" + this.lastRideDate +
                ", rideList=" + this.rideIdList +
                ", vCard=" + this.vCard +
                '}';
    }
}
