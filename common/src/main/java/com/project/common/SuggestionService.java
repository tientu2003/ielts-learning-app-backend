package com.project.common;

import com.project.common.dto.BasicExamDTO;

import java.util.List;

public interface SuggestionService {

    void generateNewRecommendation();

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
                                      List<LanguageProficiencyDTO> topicProficiencies, Boolean isDifficulty) {

        double topicMatchScore = calculateTopicMatchScore(exam.getTopics(), weakestTopics);
        if(isDifficulty) {
            double difficultyScore = calculateDifficultyScore(exam.getDifficulties(), topicProficiencies);
            // Combine scores with weights (can be adjusted)
            return (topicMatchScore * 0.65) + (difficultyScore * 0.35);

        }else {
            return topicMatchScore;
        }

    }

    default double calculateTopicMatchScore(List<String> examTopics, List<String> weakestTopics) {
        long matchingTopics = examTopics.stream()
                .filter(weakestTopics::contains)
                .count();
        return (double) matchingTopics / examTopics.size();
    }

    default double calculateDifficultyScore(List<Double> examDifficulties,
                                            List<LanguageProficiencyDTO> topicProficiencies) {
        // Calculate average user proficiency
        double avgProficiency = topicProficiencies.stream()
                .mapToDouble(LanguageProficiencyDTO::getTpi)
                .average()
                .orElse(0.0);

        // Convert CEFR levels to numerical values and get average
        double examDifficulty = examDifficulties.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        // Calculate optimal difficulty (slightly above current level)
        double optimalDifficulty = avgProficiency * 1.05;

        // Return score based on how close the exam difficulty is to optimal difficulty
        return 1.0 - Math.abs(examDifficulty/0.743 - optimalDifficulty);
    }
}
