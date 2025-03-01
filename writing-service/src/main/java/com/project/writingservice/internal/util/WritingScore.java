package com.project.writingservice.internal.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WritingScore {
    private Double taskAchievementScore;
    private String taComment;
    private Double coherenceAndCohesionScore;
    private String ccComment;
    private Double lexicalResourceScore;
    private String lrComment;
    private Double grammaticalRangeAndAccuracyScore;
    private String graComment;
    public Double getFinalScore() {
        double score = (this.lexicalResourceScore + this.grammaticalRangeAndAccuracyScore
                + this.coherenceAndCohesionScore + this.taskAchievementScore) / 4;
        return Math.round(score * 2) / 2.0; // Rounds to the nearest 0.5
    }

}
