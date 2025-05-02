package com.project.readingservice.internal;

import com.project.common.LanguageProficiencyService;
import com.project.common.SuggestionService;
import com.project.common.dto.BasicExamDTO;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.UserReadingService;
import com.project.readingservice.external.data.ReadingAnswer;
import com.project.readingservice.external.user.DetailRecord;
import com.project.readingservice.external.user.UserAnswer;
import com.project.readingservice.external.util.ReadingScore;
import com.project.readingservice.external.util.UserService;
import com.project.readingservice.internal.model.user.UserAnswerHistory;
import com.project.readingservice.internal.model.user.UserReadingRepository;
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
public class UserReadingServiceImp implements UserReadingService {


    final CRUDReadingService crudReadingService;

    final UserReadingRepository userReadingRepository;

    final ReadingScore readingScore;

    final UserService userService;

    final SuggestionService suggestionService;

    final LanguageProficiencyService languageProficiencyService;

    @Override
    @Transactional
    public String saveUserAnswerData(UserAnswer userAnswer) {
        String userId = userService.getUserId();
        ReadingAnswer readingAnswer = crudReadingService.getAnswerTest(userAnswer.getTestId());

        List<Boolean> result = readingScore.checkAnswerAndCalculateScore(userAnswer.getAnswers(), readingAnswer.getAnswers());

        UserAnswerHistory newOne = userReadingRepository.insert(new UserAnswerHistory(userId,
                userAnswer, readingScore.getScore(), result,readingAnswer));
        return newOne.getId();
    }

    @Override
    public List<BasicUserRecordDTO> listUserReadingTestHistory() {
        String userId = userService.getUserId();
        return userReadingRepository.findAllByUserIdWithTestName(userId);
    }

    @Override
    public DetailRecord getUserDetailReadingTestHistory(String recordId) {
        Optional<UserAnswerHistory> record = userReadingRepository.findById(recordId);
        return record.map(userAnswerHistory ->
                        userAnswerHistory.toDetailReadingTestRecord(crudReadingService
                                .getAnswerTest(userAnswerHistory.getTestId())))
                .orElse(null);

    }

    @Override
    public UserSummary getReadingGeneralAssessment() {
        String userId = userService.getUserId();
        Double averageScore = userReadingRepository.calculateAverageScoreByUserId(userId);
        BasicExamDTO exam = suggestionService.getSuggestedNextExam();
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
