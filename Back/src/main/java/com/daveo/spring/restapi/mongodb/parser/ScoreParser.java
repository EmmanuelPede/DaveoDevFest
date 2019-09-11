package com.daveo.spring.restapi.mongodb.parser;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j2
public class ScoreParser {

    private Pattern scorePattern = Pattern.compile("^\\$#\\$ setting score (\\d+) for song: .+");
    private Pattern songPattern = Pattern.compile("^sending score\\. title:(.+) duration:(\\d+) artist:(.*)$");

    public void handleLine(final String line) {

        final Matcher scoreMatcher = scorePattern.matcher(line);
        final Matcher songMatcher = songPattern.matcher(line);

        String songName = null;
        String songDuration = null;
        String songArtist = null;

        String score = null;

        if (songMatcher.matches()) {
            songName = songMatcher.group(1);
            songDuration = songMatcher.group(2);
            songArtist = songMatcher.group(3);

            log.info("[AS2-TRACKER] Song: {} - Duration: {} - Artist: {}", songName, songDuration, songArtist);
        } else if (scoreMatcher.matches()) {
            score = scoreMatcher.group(1);

            log.info("[AS2-TRACKER] Score: {}", score);
        }
    }
}
