package com.daveo.spring.restapi.mongodb.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ScoreParser {

    private Pattern scorePattern = Pattern.compile("^\\$#\\$ setting score (\\d+) for song: .+");

    private Pattern songPattern = Pattern.compile("^sending score\\. title:(.+) duration:(\\d+) artist:(.*)$");

    public RideDto handleFile(final File file) {
        try {
            final String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            log.debug("[AS2-TRACKER] FileContentLength: {}.", fileContent.length());

            final String[] lines = fileContent.split("\\r?\\n");

            SongDto currSong = null;
            Long currScore = null;

            final Map<SongDto, Long> mapSongScore = new HashMap<>();
            final Map<Integer, RideDto> mapLineNbSongScore = new HashMap<>();

            for (int i = 0; i < lines.length; i++) {
                String l = lines[i];

                final RideDto rideDto = handleLine(l);

                if (rideDto != null) {
                    if (rideDto.getSong() != null) {
                        currSong = rideDto.getSong();
                    }
                    if (rideDto.getScore() != null) {
                        currScore = rideDto.getScore();
                    }

                    if (currScore != null && currSong != null) {
                        mapSongScore.put(currSong, currScore);
                        mapLineNbSongScore.put(i, new RideDto(currSong, currScore));
                        currScore = null;
                        currSong = null;
                    }
                }
            }

            log.debug("[AS2-TRACKER] List of Song And Score : {}", mapSongScore);
            log.debug("[AS2-TRACKER] List of Song And Score with Line Nb: {}", mapLineNbSongScore);

            if (!mapLineNbSongScore.isEmpty()) {
                final Integer keyOfMaxScore = mapLineNbSongScore.keySet().stream().max(Integer::compare).orElse(null);
                return mapLineNbSongScore.get(keyOfMaxScore);
            }

        } catch (IOException e) {
            log.error("[AS2-TRACKER] Erreur lors de la lecture du fichier.", e);
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
}
