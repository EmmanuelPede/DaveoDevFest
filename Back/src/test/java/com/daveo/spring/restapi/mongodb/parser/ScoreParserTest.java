package com.daveo.spring.restapi.mongodb.parser;

import com.daveo.spring.restapi.mongodb.repo.RideRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ScoreParserTest {

    @Autowired
    private RideRepository rideRepository;

    @Test
    public void scoreParser_songPattern_test() {

        final ScoreParser scoreParser = new ScoreParser(this.rideRepository);
        final String line = "sending score. title:She Moves Like A Knife duration:333099 artist:PERTURBATOR";

        scoreParser.handleLine(line);
    }

    @Test
    public void scoreParser_scorePattern_test() {

        final ScoreParser scoreParser = new ScoreParser(this.rideRepository);
        final String line = "$#$ setting score 559828 for song: She Moves Like A Knife";

        scoreParser.handleLine(line);
    }
}
