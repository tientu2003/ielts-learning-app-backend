package com.project.writingservice.internal;

import com.project.writingservice.internal.util.WritingScore;

public interface ScoringService {
    WritingScore getWritingScoreTask1(String context, String diagram_url, String answer);
    WritingScore getWritingScoreTask2(String context, String answer);
}
