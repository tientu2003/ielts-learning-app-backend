package com.project.listeningservice.external.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListeningSummary {

    private String userId;

    private Double averageScore;

    private String personalRecommendation;

    private String nextTestId;

    private String testName;
}
