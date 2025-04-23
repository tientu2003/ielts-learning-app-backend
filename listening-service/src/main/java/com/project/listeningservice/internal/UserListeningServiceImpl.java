package com.project.listeningservice.internal;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.Topic;
import com.project.common.TopicProficiency;
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
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserListeningServiceImpl extends LanguageProficiencyService implements UserListeningService {

    final UserListeningRepository userListeningRepository;

    final ListeningScore listeningScore;

    final CrudListeningService crudListeningService;

    final UserService userService;

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
               listeningScore.getScore(count), listeningAnswer.getNumberQuestions(), listeningAnswer.getTopics()));
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

        // TODO do the personal and next suggestion here
        List<BasicExamDTO> exams = crudListeningService.listAllListeningExams();
        // Select a random exam for next test ID and name1
        BasicExamDTO nextTest = exams.isEmpty() ? null : exams.get(new Random().nextInt(exams.size()));

        return UserSummary.builder()
                .userId(userId)
                .averageScore(averageScore)
                .nextTestId(nextTest != null ? nextTest.getId() : null)
                .testName(nextTest != null ? nextTest.getTestName() : null)
                .build();
    }

    @Override
    public List<LanguageProficiencyDTO> getAllTopicProficiencyIndexs(){
        List<Topic> topics = userListeningRepository.findDistinctTopicsByUserId(userService.getUserId());
        return topics.stream().map(this::getTopicProficiencyDTO).toList();
    }

    private LanguageProficiencyDTO getTopicProficiencyDTO(Topic topic) {
        String userId = userService.getUserId();
        List<TopicProficiency> topicProficiencies = userListeningRepository.findAllTopicProficiencyByUserIdAndTopic(userId, topic);
        if(topicProficiencies.isEmpty()) return null;
        Double tpi = topicProficiencyIndexCalculator(topicProficiencies);
        return LanguageProficiencyDTO.builder()
                .topic(topic)
                .tpi(tpi)
                .tci(topicConsistencyIndexCalculator(topicProficiencies,tpi))
                .build();
    }
}

