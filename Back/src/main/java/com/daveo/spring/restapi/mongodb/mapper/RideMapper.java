package com.daveo.spring.restapi.mongodb.mapper;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.dto.SongDto;
import com.daveo.spring.restapi.mongodb.model.Ride;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    public RideDto toRideDto(final Ride ride) {
        if (ride == null) {
            return null;
        }

        final RideDto rideDto = new RideDto();
        rideDto.setKey(ride.getKey());
        rideDto.setScore(ride.getScore());
        rideDto.setCustomerId(ride.getCustomerId());

        final SongDto songDto = new SongDto();

        songDto.setSongArtist(ride.getSongArtist());
        songDto.setSongDuration(ride.getSongDuration());
        songDto.setSongName(ride.getSongName());

        rideDto.setSong(songDto);

        return rideDto;
    }
}
