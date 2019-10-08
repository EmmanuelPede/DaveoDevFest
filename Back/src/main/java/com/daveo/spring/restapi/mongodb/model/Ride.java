package com.daveo.spring.restapi.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ride")
public class Ride {

    @Id
    private String id;

    private String songName;

    private String songDuration;

    private String SongArtist;

    private Long score;

    private Long key;

    private Date created;

    private String customerId;

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + this.id + '\'' +
                ", songName='" + this.songName + '\'' +
                ", songDuration='" + this.songDuration + '\'' +
                ", SongArtist='" + this.SongArtist + '\'' +
                ", score=" + this.score +
                ", key=" + this.key +
                ", created=" + this.created +
                '}';
    }
}
