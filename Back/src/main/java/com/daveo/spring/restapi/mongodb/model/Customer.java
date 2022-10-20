package com.daveo.spring.restapi.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private String telephone;

    @JsonProperty("lastScore")
    private Long lastScore;

    @JsonProperty("bestScore")
    private Long bestScore;

    private Boolean active;

    private Date created;

    @JsonProperty("lastRideDate")
    private Date lastRideDate;

    @JsonProperty("lastSelectDate")
    private Date lastSelectDate;

    @JsonProperty("rideIdList")
    private Set<String> rideIdList;

    @JsonProperty("vCard")
    private String vCard;

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                ", telephone='" + this.telephone + '\'' +
                ", lastScore=" + this.lastScore +
                ", bestScore=" + this.bestScore +
                ", active=" + this.active +
                ", created=" + this.created +
                ", lastRideDate=" + this.lastRideDate +
                ", lastSelectDate=" + this.lastSelectDate +
                ", rideList=" + this.rideIdList +
                ", vCard=" + this.vCard +
                '}';
    }
}
