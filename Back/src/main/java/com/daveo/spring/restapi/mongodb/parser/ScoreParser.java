package com.daveo.spring.restapi.mongodb.parser;

import com.daveo.spring.restapi.mongodb.dto.RideDto;
import com.daveo.spring.restapi.mongodb.dto.SongDto;
import com.daveo.spring.restapi.mongodb.model.Ride;
import com.daveo.spring.restapi.mongodb.repo.RideRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ScoreParser {

    private final Pattern scorePattern = Pattern.compile("^\\$#\\$ setting score (\\d+) for song: .+");

    private final Pattern songPattern = Pattern.compile("^sending score\\. title:(.+) duration:(\\d+) artist:(.*)$");

    private final RideRepository rideRepository;

    public ScoreParser(final RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Transactional
    public RideDto handleFile(final BufferedReader reader, final Long nbLineOfFile) {

        final String fileContent = this.readAllLines(reader);

        if (fileContent != null && !fileContent.isEmpty()) {
            log.debug("[AS2-TRACKER] FileContentLength: {}.", fileContent.length());

            final String[] allNewLines = fileContent.split("\\r?\\n");
            final int nbNewLines = allNewLines.length;
            final long offsetKey = nbLineOfFile - nbNewLines;

            final Map<Long, RideDto> mapLineNbSongScore = this.extractAllRide(allNewLines, offsetKey);

            log.debug("[AS2-TRACKER] List of Song And Score with Line Nb: {}", mapLineNbSongScore);

            if (!mapLineNbSongScore.isEmpty()) {

                mapLineNbSongScore.forEach((key, r) -> {

                    final Optional<Ride> optRide = this.rideRepository.findByKey(key);
                    if (!optRide.isPresent()) {
                        // Save score if not present in database
                        final Ride ride = new Ride(null,
                                r.getSong().getSongName(),
                                r.getSong().getSongDuration(),
                                r.getSong().getSongArtist(),
                                r.getScore(), key, new Date(), null);
                        this.rideRepository.save(ride);
                    }
                });

                final Long keyOfMaxScore = mapLineNbSongScore.keySet().stream()
                        .max(Long::compare)
                        .orElse(null);

                final RideDto rideDto = mapLineNbSongScore.get(keyOfMaxScore);
                if (keyOfMaxScore != null) {
                    rideDto.setKey(keyOfMaxScore);
                }

                return rideDto;
            }
        }

        return null;
    }

    public RideDto handleLine(final String line) {

        final Matcher scoreMatcher = this.scorePattern.matcher(line);
        final Matcher songMatcher = this.songPattern.matcher(line);

        String songName = null;
        String songDuration = null;
        String songArtist = null;

        Long score = null;

        if (songMatcher.matches()) {
            songName = songMatcher.group(1);
            songDuration = songMatcher.group(2);
            songArtist = songMatcher.group(3);

            log.debug("[AS2-TRACKER] Song: {} - Duration: {} - Artist: {}", songName, songDuration, songArtist);
        } else if (scoreMatcher.matches()) {
            score = Long.valueOf(scoreMatcher.group(1));

            log.debug("[AS2-TRACKER] Score: {}", score);
        }

        SongDto songDto = null;
        if (songName != null && !songName.isEmpty()) {
            songDto = new SongDto();
            songDto.setSongName(songMatcher.group(1));
            songDto.setSongDuration(songMatcher.group(2));
            songDto.setSongArtist(songMatcher.group(3));
        }

        if (score != null || songDto != null) {
            return new RideDto(songDto, score);
        }

        return null;
    }

    private String readAllLines(final BufferedReader reader) {
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Map<Long, RideDto> extractAllRide(final String[] lines, final long offsetKey) {
        final Map<Long, RideDto> mapLineNbSongScore = new HashMap<>();

        SongDto currSong = null;
        Long currScore = null;

        for (int i = 0; i < lines.length; i++) {
            final String l = lines[i];

            final RideDto rideDto = this.handleLine(l);

            if (rideDto != null) {
                if (rideDto.getSong() != null) {
                    currSong = rideDto.getSong();
                }
                if (rideDto.getScore() != null) {
                    currScore = rideDto.getScore();
                }

                if (currScore != null && currSong != null) {
                    mapLineNbSongScore.put(offsetKey + i, new RideDto(currSong, currScore));
                    currScore = null;
                    currSong = null;
                }
            }
        }
        return mapLineNbSongScore;
    }
}
