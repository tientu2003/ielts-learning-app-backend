package com.project.common;

import com.project.common.dto.BasicExamDTO;

public interface SuggestionService {

    String getPersonalRecommendation();

    BasicExamDTO getSuggestedNextExam();
}
