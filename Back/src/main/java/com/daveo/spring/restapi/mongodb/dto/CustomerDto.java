package com.daveo.spring.restapi.mongodb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerDto {

    private String id;

    private String name;

    private String email;

    private Long lastScore;

    private Long bestScore;

    private Boolean active;

    private Date created;

    private Date lastRideDate;

    private String vCard;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                ", lastScore=" + this.lastScore +
                ", bestScore=" + this.bestScore +
                ", active=" + this.active +
                ", created=" + this.created +
                ", lastRideDate=" + this.lastRideDate +
                '}';
    }
}
