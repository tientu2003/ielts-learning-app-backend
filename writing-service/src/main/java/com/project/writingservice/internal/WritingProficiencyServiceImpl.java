package com.project.writingservice.internal;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.dto.TopicAverage;
import com.project.writingservice.external.UserService;
import com.project.writingservice.internal.entity.user.UserWritingRecordRepository;
import com.project.writingservice.internal.util.WritingScore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WritingProficiencyServiceImpl extends LanguageProficiencyService {

    final UserWritingRecordRepository userWritingRecordRepository;

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
        Double average = userWritingRecordRepository.getAverageFinalScoreByUserId(userService.getUserId());
        return LanguageProficiencyDTO.builder()
                .tci(tci)
                .tpi(tpi)
                .averageScore(average == null ? 0.0 : average)
                .build();
    }

    @Override
    public List<String> getAllTopicsByUserId() {
        String userId = userService.getUserId();
        return userWritingRecordRepository.findDistinctTopicsByUserId(userId);
    }

    @Override
    public List<LanguageProficiencyDTO> getAllTopicProficiencyIndexs(List<String> topics) {
        String userId = userService.getUserId();
        if(topics == null || topics.isEmpty()) {
            topics = userWritingRecordRepository.findDistinctTopicsByUserId(userId);
        }
        List<TopicAverage> averages = userWritingRecordRepository.calculateAverageScoreByUserIdGroupByTopic(userId);

        Map<String, Double> averageMap = averages.parallelStream()
                .collect(Collectors.toMap(TopicAverage::getTopic, TopicAverage::getAverageScore));

        return topics.parallelStream()
                .map(topic -> getTopicProficiencyDTO(topic, averageMap.get(topic), userId))
                .filter(Objects::nonNull)
                .toList();    }

    private LanguageProficiencyDTO getTopicProficiencyDTO(String topic, Double averageScore, String userId) {
        List<WritingScore> writingScores = userWritingRecordRepository.findAllWritingScoreByUserIdAndTopic(userId, topic);
        if(writingScores.isEmpty()) return null;
        return LanguageProficiencyDTO.builder()
                .topic(topic)
                .tpi(averageScore/9.0)
                .tci(writingTciCalculator(writingScores,averageScore/9.0))
                .averageScore(averageScore)
                .build();
    }

    private Double writingTciCalculator(List<WritingScore> scores, Double mean) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        double sumSquaredDiff = scores.parallelStream().mapToDouble(e -> {
            double diff = e.getFinalScore()/9.0 - mean;
            return diff * diff;
        }).sum();
        double variance = sumSquaredDiff / scores.size();
        double stdDeviation = Math.sqrt(variance);
        return 1 - Math.round(stdDeviation * 1000.0) / 1000.0; // làm tròn đến 3 chữ số thập phân
    }


}
