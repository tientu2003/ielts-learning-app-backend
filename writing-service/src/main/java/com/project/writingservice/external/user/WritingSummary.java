package com.project.writingservice.external.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WritingSummary {

    private Double averageScore;
    private String personalRecommendation;
    private String nextTestId;
    private String testName;
    private String totalTime;


}
