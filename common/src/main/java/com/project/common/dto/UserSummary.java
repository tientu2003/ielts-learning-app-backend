package com.project.common.dto;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.constraints.Topic;
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

    private List<Topic> topics;

    List<LanguageProficiencyDTO> languageProficiencies;

    private LanguageProficiencyDTO skillLanguageProficiency;
}
