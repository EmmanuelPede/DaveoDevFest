package com.daveo.spring.restapi.mongodb.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SongDto {
    private String songName;

    private String songDuration;

    private String songArtist;

    @Override
    public String toString() {
        return "SongDto{" +
                "songName='" + this.songName + '\'' +
                ", songDuration='" + this.songDuration + '\'' +
                ", songArtist='" + this.songArtist + '\'' +
                '}';
    }
}
