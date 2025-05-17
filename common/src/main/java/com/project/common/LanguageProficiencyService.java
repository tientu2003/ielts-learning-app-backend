package com.project.common;

import java.util.List;

public abstract class LanguageProficiencyService {

    protected Double topicProficiencyIndexCalculator(List<TopicProficiency> topicProficiencies) {
        return topicProficiencies.stream().mapToDouble(TopicProficiency::getTopicProficiencyIndex).average().orElse(0.0);
    }

    protected Double topicConsistencyIndexCalculator(List<TopicProficiency> topicProficiencies, Double topicProficiencyIndex) {
        double sumSquaredDiff = topicProficiencies.stream()
                .mapToDouble(tp -> tp.getFinalWeight() * Math.pow(tp.getTopicProficiencyIndex() - topicProficiencyIndex, 2))
                .sum();

        double sumWeights = topicProficiencies.stream()
                .mapToDouble(TopicProficiency::getFinalWeight)
                .sum();

        if (sumWeights == 0) {
            return 0.0;
        }

        double weightedVariance = sumSquaredDiff / sumWeights;

        return 1 - Math.sqrt(weightedVariance);
    }

    public abstract LanguageProficiencyDTO getSkillProficiencyDTO();

    public abstract List<LanguageProficiencyDTO> getAllTopicProficiencyIndexs(List<String> topics);

    public abstract List<String> getAllTopicsByUserId();

}
