package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicAverage {
    private String topic;
    private Double averageScore;
    // getters, setters
}
