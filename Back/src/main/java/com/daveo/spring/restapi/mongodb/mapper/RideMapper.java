package com.daveo.spring.restapi.mongodb.mapper;

import com.daveo.spring.restapi.mongodb.model.Ride;
import com.daveo.spring.restapi.mongodb.parser.RideDto;
import com.daveo.spring.restapi.mongodb.parser.SongDto;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    public RideDto toRideDto(final Ride ride) {
        final RideDto rideDto = new RideDto();
        rideDto.setKey(ride.getKey());
        rideDto.setScore(ride.getScore());

        final SongDto songDto = new SongDto();

        songDto.setSongArtist(ride.getSongArtist());
        songDto.setSongDuration(ride.getSongDuration());
        songDto.setSongName(ride.getSongName());

        rideDto.setSong(songDto);

        return rideDto;
    }
}
