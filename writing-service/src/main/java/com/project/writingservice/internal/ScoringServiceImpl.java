package com.project.writingservice.internal;

import com.project.writingservice.internal.util.WritingScore;
import org.springframework.stereotype.Service;

@Service
public class ScoringServiceImpl implements ScoringService {

    @Override
    public WritingScore getWritingScoreTask1(String context, String diagram_url, String answer) {
        return WritingScore.builder()
                .taskAchievementScore(9.0)
                .taComment("test")
                .lexicalResourceScore(8.0)
                .lrComment("test")
                .coherenceAndCohesionScore(7.0)
                .ccComment("test")
                .grammaticalRangeAndAccuracyScore(6.0)
                .graComment("test")
                .build();
    }

    @Override
    public WritingScore getWritingScoreTask2(String context, String answer) {
        return WritingScore.builder()
                .taskAchievementScore(9.0)
                .taComment("test")
                .lexicalResourceScore(8.0)
                .lrComment("test")
                .coherenceAndCohesionScore(7.0)
                .ccComment("test")
                .grammaticalRangeAndAccuracyScore(6.0)
                .graComment("test")
                .build();
    }
}
