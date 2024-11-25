package com.project.readingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GeneralAssessment {

    private UUID userId;

    private Double averageScore;

    private List<Score> allScores;

    private String personalRecommendation;

    private String nextTestId;

    @Data
    @Builder
    public static class Score {

        Date timestamp;

        Double score;

    }
}
