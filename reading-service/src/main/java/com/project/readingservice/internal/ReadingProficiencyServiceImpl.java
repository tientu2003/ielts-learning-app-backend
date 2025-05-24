package com.project.readingservice.internal;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.TopicProficiency;
import com.project.common.dto.MetricAverage;
import com.project.common.dto.TopicAverage;
import com.project.readingservice.internal.util.UserService;
import com.project.readingservice.internal.model.user.UserReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadingProficiencyServiceImpl extends LanguageProficiencyService {

    final UserReadingRepository userReadingRepository;

    final UserService userService;

    @Override
    public LanguageProficiencyDTO getSkillProficiencyDTO() {
        List<LanguageProficiencyDTO> list = getAllTopicProficiencyIndexs(getAllTopicsByUserId());
        Double tci = null;
        Double tpi = null;
        if(list != null && !list.isEmpty()) {
            tci = list.parallelStream().mapToDouble(LanguageProficiencyDTO::getTci).average().orElse(0.0);
            tpi = list.parallelStream().mapToDouble(LanguageProficiencyDTO::getTpi).average().orElse(0.0);
        }

        MetricAverage metrics = userReadingRepository.calculateAverageMetricsByUserId(userService.getUserId());
        return LanguageProficiencyDTO.builder()
                .tci(tci)
                .tpi(tpi)
                .averageDifficulty(metrics == null ? 0.0 : metrics.getAverageDifficulty())
                .averageScore(metrics == null ? 0.0 : metrics.getAverageScore())
                .averageAccuracy(metrics == null ? 0.0 : metrics.getAverageAccuracy())
                .build();

    }

    @Override
    public List<String> getAllTopicsByUserId() {
        String userId = userService.getUserId();
        return userReadingRepository.findDistinctTopicsByUserId(userId);
    }

    @Override
    public List<LanguageProficiencyDTO> getAllTopicProficiencyIndexs(List<String> topics) {
        String userId = userService.getUserId();
        if(topics == null || topics.isEmpty()) {
            topics = userReadingRepository.findDistinctTopicsByUserId(userId);
        }
        List<TopicAverage> averages = userReadingRepository.calculateAverageScoreByUserIdGroupByTopic(userId);

        Map<String, Double> averageMap = averages.parallelStream()
                .collect(Collectors.toMap(TopicAverage::getTopic, TopicAverage::getAverageScore));

        return topics.parallelStream()
                .map(topic -> getTopicProficiencyDTO(topic, averageMap.get(topic), userId))
                .filter(Objects::nonNull)
                .toList();    }


    private LanguageProficiencyDTO getTopicProficiencyDTO(String topic, Double averageScore, String userId) {
        List<TopicProficiency> topicProficiencies = userReadingRepository.findAllTopicProficiencyByUserIdAndTopic(userId, topic);
        Double averageDifficult = topicProficiencies.parallelStream().mapToDouble(TopicProficiency::getDifficulty).average().orElse(0.0);
        Double averageAccuracy = topicProficiencies.parallelStream().mapToDouble(TopicProficiency::getAccuracy).average().orElse(0.0);

        if(topicProficiencies.isEmpty()) return null;
        Double tpi = topicProficiencyIndexCalculator(topicProficiencies);
        return LanguageProficiencyDTO.builder()
                .topic(topic)
                .tpi(tpi)
                .tci(topicConsistencyIndexCalculator(topicProficiencies,tpi))
                .averageAccuracy(averageAccuracy)
                .averageScore(averageScore)
                .averageDifficulty(averageDifficult)
                .build();
    }
}
