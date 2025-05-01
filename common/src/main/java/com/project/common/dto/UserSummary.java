package com.project.common.dto;

import com.project.common.LanguageProficiencyDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSummary {
    private String userId;

    private Double averageScore;

    private String personalRecommendation;

    private String nextTestId;

    private String testName;

    private List<String> topics;

    private LanguageProficiencyDTO skillLanguageProficiency;
}
