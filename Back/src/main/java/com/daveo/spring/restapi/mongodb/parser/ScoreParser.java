package com.daveo.spring.restapi.mongodb.parser;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ScoreParser {

    private final Pattern scorePattern = Pattern.compile("^\\$#\\$ setting score (\\d+) for song: .+");

    private final Pattern songPattern = Pattern.compile("^sending score\\. title:(.+) duration:(\\d+) artist:(.*)$");

    public RideDto handleFile(final BufferedReader reader) {

        final String fileContent = readAllLines(reader);

        if (fileContent != null && !fileContent.isEmpty()) {
            log.debug("[AS2-TRACKER] FileContentLength: {}.", fileContent.length());

            final String[] allNewLines = fileContent.split("\\r?\\n");

            final Map<Integer, RideDto> mapLineNbSongScore = extractAllRide(allNewLines);

            log.debug("[AS2-TRACKER] List of Song And Score with Line Nb: {}", mapLineNbSongScore);

            if (!mapLineNbSongScore.isEmpty()) {
                final Integer keyOfMaxScore = mapLineNbSongScore.keySet().stream()
                        .max(Integer::compare)
                        .orElse(null);

                return mapLineNbSongScore.get(keyOfMaxScore);
            }
        }

        return null;
    }

    public RideDto handleLine(final String line) {

        final Matcher scoreMatcher = scorePattern.matcher(line);
        final Matcher songMatcher = songPattern.matcher(line);

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

    private Map<Integer, RideDto> extractAllRide(final String[] lines) {
        final Map<Integer, RideDto> mapLineNbSongScore = new HashMap<>();

        SongDto currSong = null;
        Long currScore = null;

        for (int i = 0; i < lines.length; i++) {
            final String l = lines[i];

            final RideDto rideDto = handleLine(l);

            if (rideDto != null) {
                if (rideDto.getSong() != null) {
                    currSong = rideDto.getSong();
                }
                if (rideDto.getScore() != null) {
                    currScore = rideDto.getScore();
                }

                if (currScore != null && currSong != null) {
                    mapLineNbSongScore.put(i, new RideDto(currSong, currScore));
                    currScore = null;
                    currSong = null;
                }
            }
        }
        return mapLineNbSongScore;
    }
}
