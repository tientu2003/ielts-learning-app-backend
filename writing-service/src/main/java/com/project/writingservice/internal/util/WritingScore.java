package com.project.writingservice.internal.util;

import lombok.Data;

@Data
public class WritingScore {
    private Double taskAchievementScore;
    private Double coherenceAndCohesionScore;
    private Double lexicalResourceScore;
    private Double grammaticalRangeAndAccuracyScore;
}
