package com.daveo.spring.restapi.mongodb.parser;

import org.junit.Test;

public class ScoreParserTest {

    @Test
    public void scoreParser_songPattern_test() {

        final ScoreParser scoreParser = new ScoreParser();
        final String line = "sending score. title:She Moves Like A Knife duration:333099 artist:PERTURBATOR";

        scoreParser.handleLine(line);
    }

    @Test
    public void scoreParser_scorePattern_test() {

        final ScoreParser scoreParser = new ScoreParser();
        final String line = "$#$ setting score 559828 for song: She Moves Like A Knife";

        scoreParser.handleLine(line);
    }
}
