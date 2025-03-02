package com.project.writingservice.external.user;

import com.project.writingservice.internal.util.WritingScore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailRecord {
    private Integer task;
    private String context;
    private String diagram_url;
    private String userAnswer;
    private String duration;
    private WritingScore scores;
    private Double finalScore;
}
