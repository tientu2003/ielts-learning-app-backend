package com.project.readingservice.internal;

import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.UserReadingService;
import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.user.BasicReadingHistory;
import com.project.readingservice.external.user.DetailReadingTestRecord;
import com.project.readingservice.external.user.GeneralAssessment;
import com.project.readingservice.external.user.UserAnswer;
import com.project.readingservice.external.util.ReadingScore;
import com.project.readingservice.external.util.UserService;
import com.project.readingservice.internal.model.data.MongoReadingTest;
import com.project.readingservice.internal.model.user.UserAnswerHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserReadingServiceImp implements UserReadingService {


    final CRUDReadingService crudReadingService;

    final UserReadingRepository userReadingRepository;

    final ReadingTestRepository readingTestRepository;

    final ReadingScore readingScore;

    final UserService userService;

    @Override
    @Transactional
    public DetailReadingTestRecord saveUserAnswerData(UserAnswer userAnswer) {
        String userId = userService.getUserId();
        log.info("User {} save user answer data", userId);

        AnswerData testAnswer = crudReadingService.getAnswerTest(userAnswer.getTestId());

        List<Boolean> result = readingScore.checkAnswerAndCalculateScore(userAnswer.getAnswers(), testAnswer.getAnswers());

        List<String> tempSuggestions = new ArrayList<>();

        IntStream.range(0, 40).forEach(index ->tempSuggestions.add(""));


        UserAnswerHistory newOne = userReadingRepository.insert(new UserAnswerHistory(userId,
                userAnswer,
                readingScore.getScore(),
                result,
                tempSuggestions));

        return newOne.toDetailReadingTestRecord(testAnswer);
    }

    @Override
    public List<BasicReadingHistory> listUserReadingTestHistory() {

        String userId = userService.getUserId();

        List<UserAnswerHistory> datas = userReadingRepository.findAllByUserIdLike(userId);

        return datas.stream().map(
                data -> {
                    Optional<MongoReadingTest> returnTest = readingTestRepository.findById(data.getTestId());
                    return returnTest
                            .map(mongoReadingTest ->
                                    data.toBasicReadingHistory(mongoReadingTest.getTestName()))
                            .orElse(null);
                }
        ).toList();
    }

    @Override
    public DetailReadingTestRecord getUserDetailReadingTestHistory(String recordId) {
        Optional<UserAnswerHistory> record = userReadingRepository.findById(recordId);
        return record.map(userAnswerHistory ->
                        userAnswerHistory.toDetailReadingTestRecord(crudReadingService
                                .getAnswerTest(userAnswerHistory.getTestId())))
                .orElse(null);

    }

    @Override
    public GeneralAssessment getReadingGeneralAssessment() {
        String userId = userService.getUserId();

        List<UserAnswerHistory> AllRecord = userReadingRepository.findAllByUserIdLike(userId);
        List<GeneralAssessment.Score> allScore = new ArrayList<>();

        double totalScore = 0.0;

        for (UserAnswerHistory record : AllRecord) {
            allScore.add(GeneralAssessment.Score.builder()
                            .score(record.getScore())
                            .timestamp(record.getCreatedAt())
                            .build());
            totalScore = totalScore + record.getScore();
        }

        return GeneralAssessment.builder()
            .userId(userId)
            .averageScore(totalScore/AllRecord.size())
            .allScores(allScore)
            .personalRecommendation(null)
            .nextTestId(null)
            .build();
    }

}
