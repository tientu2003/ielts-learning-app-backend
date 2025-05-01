package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricAverage {
    private Double averageDifficulty;
    private Double averageScore;
    private Double averageAccuracy;
}
