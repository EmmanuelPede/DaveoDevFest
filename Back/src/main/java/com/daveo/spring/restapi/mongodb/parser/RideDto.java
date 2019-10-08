package com.daveo.spring.restapi.mongodb.parser;

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

    private SongDto song;

    private Long score;

    private Long key;

    public RideDto(final SongDto song, final Long score) {
        this.song = song;
        this.score = score;
    }

    @Override
    public String toString() {
        return "RideDto{" +
                "song=" + this.song +
                ", score=" + this.score +
                '}';
    }
}
