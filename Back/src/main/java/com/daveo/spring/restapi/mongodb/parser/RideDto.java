package com.daveo.spring.restapi.mongodb.parser;

/**
 * Ride = sondDto + score
 */
public class RideDto {

    private SongDto song;

    private Long score;

    public RideDto(SongDto song, Long score) {
        this.song = song;
        this.score = score;
    }

    public SongDto getSong() {
        return song;
    }

    public RideDto setSong(SongDto song) {
        this.song = song;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public RideDto setScore(Long score) {
        this.score = score;
        return this;
    }

    @Override public String toString() {
        return "RideDto{" +
                "song=" + song +
                ", score=" + score +
                '}';
    }
}
