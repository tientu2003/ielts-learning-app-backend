package com.project.common;

import com.project.common.constraints.CefrLevel;
import com.project.common.dto.BasicExamDTO;

import java.util.List;

public interface SuggestionService {

    String getPersonalRecommendation();

    BasicExamDTO getSuggestedNextExam();

    record ExamScore(BasicExamDTO exam, double score) {
        public BasicExamDTO getExam() {
            return exam;
        }

        public double getScore() {
            return score;
        }
    }

    default double calculateExamScore(BasicExamDTO exam, List<String> weakestTopics,
                                      List<LanguageProficiencyDTO> topicProficiencies) {
        double topicMatchScore = calculateTopicMatchScore(exam.getTopics(), weakestTopics);
        double difficultyScore = calculateDifficultyScore(exam.getLevels(), topicProficiencies);

        // Combine scores with weights (can be adjusted)
        return (topicMatchScore * 0.65) + (difficultyScore * 0.35);
    }

    default double calculateTopicMatchScore(List<String> examTopics, List<String> weakestTopics) {
        long matchingTopics = examTopics.stream()
                .filter(weakestTopics::contains)
                .count();
        return (double) matchingTopics / examTopics.size();
    }

    default double calculateDifficultyScore(List<CefrLevel> examLevels,
                                            List<LanguageProficiencyDTO> topicProficiencies) {
        // Calculate average user proficiency
        double avgProficiency = topicProficiencies.stream()
                .mapToDouble(LanguageProficiencyDTO::getTpi)
                .average()
                .orElse(0.0);

        // Convert CEFR levels to numerical values and get average
        double examDifficulty = examLevels.stream()
                .mapToDouble(this::cefrToNumericLevel)
                .average()
                .orElse(0.0);

        // Calculate optimal difficulty (slightly above current level)
        double optimalDifficulty = avgProficiency * 1.2;

        // Return score based on how close the exam difficulty is to optimal difficulty
        return 1.0 - Math.abs(examDifficulty - optimalDifficulty) / 6.0; // 6.0 is max CEFR difference
    }

    default double cefrToNumericLevel(CefrLevel level) {
        return switch (level) {
            case A1 -> 1.0;
            case A2 -> 2.0;
            case B1 -> 3.0;
            case B2 -> 4.0;
            case C1 -> 5.0;
            case C2 -> 6.0;
        };
    }
}
