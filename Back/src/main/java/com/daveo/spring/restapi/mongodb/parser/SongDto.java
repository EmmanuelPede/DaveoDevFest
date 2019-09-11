package com.daveo.spring.restapi.mongodb.parser;

public class SongDto {
    private String songName;

    private String songDuration;

    private String songArtist;

    public String getSongName() {
        return songName;
    }

    public SongDto setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public SongDto setSongDuration(String songDuration) {
        this.songDuration = songDuration;
        return this;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public SongDto setSongArtist(String songArtist) {
        this.songArtist = songArtist;
        return this;
    }

    @Override public String toString() {
        return "SongDto{" +
                "songName='" + songName + '\'' +
                ", songDuration='" + songDuration + '\'' +
                ", songArtist='" + songArtist + '\'' +
                '}';
    }
}
