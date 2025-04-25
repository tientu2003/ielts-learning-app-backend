package com.project.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.common.constraints.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageProficiencyDTO {
    private Topic topic;
    private Double tpi;
    private Double tci;
    private Double averageAccuracy;
    private Double averageScore;
    private Double averageDifficulty;
}
