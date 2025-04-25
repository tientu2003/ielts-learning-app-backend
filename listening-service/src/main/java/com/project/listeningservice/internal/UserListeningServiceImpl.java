package com.project.listeningservice.internal;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.SuggestionService;
import com.project.common.TopicProficiency;
import com.project.common.constraints.Topic;
import com.project.common.dto.BasicExamDTO;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.listeningservice.CrudListeningService;
import com.project.listeningservice.UserListeningService;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.UserAnswer;
import com.project.listeningservice.external.util.ListeningScore;
import com.project.listeningservice.external.util.UserService;
import com.project.listeningservice.internal.model.user.MongoUserHistory;
import com.project.listeningservice.internal.model.user.UserListeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserListeningServiceImpl extends LanguageProficiencyService implements UserListeningService {

    final UserListeningRepository userListeningRepository;

    final ListeningScore listeningScore;

    final CrudListeningService crudListeningService;

    final UserService userService;

    final SuggestionService suggestionService;

    @Override
    @Transactional
    public String createUserAnswer(UserAnswer userAnswer) {
        String userId = userService.getUserId();
        ListeningAnswer listeningAnswer = crudListeningService.getListeningAnswer(userAnswer.getTestId());
        List<Boolean> check = listeningScore.checkAnswerAndCalculateScore(userAnswer.getAnswers(), listeningAnswer.getAnswers());
        int count = 0;
        for (Boolean aBoolean : check) {
            if (aBoolean) {
                count++;
            }
        }
        MongoUserHistory newOne = userListeningRepository.insert(new MongoUserHistory(userAnswer, userId, check,
               listeningScore.getScore(count), listeningAnswer));
        log.info("User {} save user answer data", userId);
        return newOne.getId();
    }

    @Override
    public List<BasicUserRecordDTO> listAllListeningHistory() {
        String userId = userService.getUserId();
        return userListeningRepository.findAllByUserIdWithTestName(userId);
//        List<MongoUserHistory> records = userListeningRepository.findAllByUserIdLike(userId);
//        return records.stream().map(
//                data -> {
//                    Optional<MongoListeningExam> returnTest = listeningExamRepository.findById(data.getTestId());
//                    return returnTest
//                            .map(mongoListeningExam ->
//                                    data.toUserSimpleRecord(mongoListeningExam.getExamName()))
//                            .orElse(null);
//                }
//        ).toList();
    }

    @Override
    public DetailRecord getListeningDetailRecord(String recordId) {
        Optional<MongoUserHistory> mongoUserHistory =  userListeningRepository.findById(recordId);
        if(mongoUserHistory.isPresent()) {
            ListeningAnswer mongoListeningExam = crudListeningService.getListeningAnswer(mongoUserHistory.get().getTestId());
            return mongoUserHistory.get().toDetailRecord(mongoListeningExam);
        }
        return null;
    }

    @Override
    public UserSummary getListeningScore() {
        String userId = userService.getUserId();
        Double averageScore = userListeningRepository.calculateAverageScoreByUserId(userId);
        BasicExamDTO exam =  suggestionService.getSuggestedNextExam();
        return UserSummary.builder()
                .userId(userId)
                .averageScore(averageScore)
                .nextTestId(exam.getId())
                .testName(exam.getTestName())
                .topics(getAllTopicsByUserId())
                .personalRecommendation(suggestionService.getPersonalRecommendation())
                .skillLanguageProficiency(getSkillProficiencyDTO())
                .build();
    }

    @Override
    public LanguageProficiencyDTO getSkillProficiencyDTO() {
        List<LanguageProficiencyDTO> list = getAllTopicProficiencyIndexs(getAllTopicsByUserId());
        Double tci = list.parallelStream().mapToDouble(LanguageProficiencyDTO::getTci).average().orElse(0.0);
        Double tpi = list.parallelStream().mapToDouble(LanguageProficiencyDTO::getTci).average().orElse(0.0);
        Map<String, Double> metrics = userListeningRepository.calculateAverageMetricsByUserId(userService.getUserId());
        return LanguageProficiencyDTO.builder()
                .tci(tci)
                .tpi(tpi)
                .averageDifficulty(metrics.getOrDefault("averageDifficulty", 0.0))
                .averageScore(metrics.getOrDefault("averageScore", 0.0))
                .averageAccuracy(metrics.getOrDefault("averageAccuracy", 0.0))
                .build();
    }

    @Override
    public List<Topic> getAllTopicsByUserId() {
        String userId = userService.getUserId();
        return userListeningRepository.findDistinctTopicsByUserId(userId);
    }

    @Override
    public List<LanguageProficiencyDTO> getAllTopicProficiencyIndexs(List<Topic> topics){
        String userId = userService.getUserId();
        if(topics == null || topics.isEmpty()) {
            topics = userListeningRepository.findDistinctTopicsByUserId(userId);
        }
        Map<Topic, Double> maps =  userListeningRepository.calculateAverageScoreByUserIdGroupByTopic(userId);
        return topics.stream().map(topic -> getTopicProficiencyDTO(topic, maps.get(topic))).toList();
    }

    private LanguageProficiencyDTO getTopicProficiencyDTO(Topic topic, Double averageScore) {
        String userId = userService.getUserId();
        List<TopicProficiency> topicProficiencies = userListeningRepository.findAllTopicProficiencyByUserIdAndTopic(userId, topic);
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

