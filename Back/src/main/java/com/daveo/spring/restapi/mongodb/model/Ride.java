package com.daveo.spring.restapi.mongodb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "ride")
public class Ride {

    @Id
    private Long id;

    private String songName;
    private String songDuration;
    private int score;
    private Date created;

    public Ride() {
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", songDuration='" + songDuration + '\'' +
                ", score=" + score +
                ", created=" + created +
                '}';
    }
}
