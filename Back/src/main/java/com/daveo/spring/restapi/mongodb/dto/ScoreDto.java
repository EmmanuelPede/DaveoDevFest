package com.daveo.spring.restapi.mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto {
    private Long score;

    @Override
    public String toString() {
        return "ScoreDto{" +
                "score=" + score +
                '}';
    }
}
