package com.daveo.spring.restapi.mongodb.parser;

public class SongScoreDto {
    private SongDto song;

    private Long score;

    public SongScoreDto(SongDto song, Long score) {
        this.song = song;
        this.score = score;
    }

    public SongDto getSong() {
        return song;
    }

    public SongScoreDto setSong(SongDto song) {
        this.song = song;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public SongScoreDto setScore(Long score) {
        this.score = score;
        return this;
    }

    @Override public String toString() {
        return "SongScoreDto{" +
                "song=" + song +
                ", score=" + score +
                '}';
    }
}
