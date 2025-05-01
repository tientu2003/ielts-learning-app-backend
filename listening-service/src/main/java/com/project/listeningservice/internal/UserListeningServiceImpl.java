package com.project.listeningservice.internal;

import com.project.common.LanguageProficiencyService;
import com.project.common.SuggestionService;
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


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserListeningServiceImpl implements UserListeningService {

    final UserListeningRepository userListeningRepository;

    final ListeningScore listeningScore;

    final CrudListeningService crudListeningService;

    final UserService userService;

    final SuggestionService suggestionService;

    final LanguageProficiencyService languageProficiencyService;

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
                .topics(languageProficiencyService.getAllTopicsByUserId())
                .personalRecommendation(suggestionService.getPersonalRecommendation())
                .skillLanguageProficiency(languageProficiencyService.getSkillProficiencyDTO())
                .build();
    }
}

