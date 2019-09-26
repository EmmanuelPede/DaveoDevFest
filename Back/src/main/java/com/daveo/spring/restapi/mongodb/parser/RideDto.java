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

    public void setSong(SongDto song) {
        this.song = song;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override public String toString() {
        return "RideDto{" +
                "song=" + song +
                ", score=" + score +
                '}';
    }
}
