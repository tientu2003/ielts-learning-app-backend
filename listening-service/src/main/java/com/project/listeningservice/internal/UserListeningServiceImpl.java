package com.project.listeningservice.internal;

import com.project.listeningservice.CrudListeningService;
import com.project.listeningservice.UserListeningService;
import com.project.listeningservice.external.data.IdName;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.ListeningSummary;
import com.project.listeningservice.external.user.UserAnswer;
import com.project.listeningservice.external.user.UserSimpleRecord;
import com.project.listeningservice.external.util.ListeningScore;
import com.project.listeningservice.internal.model.data.ListeningExamRepository;
import com.project.listeningservice.internal.model.data.MongoListeningExam;
import com.project.listeningservice.internal.model.user.MongoUserHistory;
import com.project.listeningservice.internal.model.user.UserListeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
public class UserListeningServiceImpl implements UserListeningService {

    final UserListeningRepository userListeningRepository;

    final ListeningScore listeningScore;

    final CrudListeningService crudListeningService;

    final ListeningExamRepository listeningExamRepository;

    public UserListeningServiceImpl(UserListeningRepository userListeningRepository,
                                    ListeningScore listeningScore,
                                    CrudListeningService crudListeningService,
                                    ListeningExamRepository listeningExamRepository) {
        this.userListeningRepository = userListeningRepository;
        this.listeningScore = listeningScore;
        this.crudListeningService = crudListeningService;
        this.listeningExamRepository = listeningExamRepository;
    }

    @Override
    @Transactional
    public String createUserAnswer(UserAnswer userAnswer, String userId) {
        List<String> testAnswer = crudListeningService.getListeningAnswer(userAnswer.getTestId()).getAnswers();
        List<Boolean> check = listeningScore.checkAnswerAndCalculateScore(userAnswer.getAnswers(), testAnswer);
        int count = 0;
        for (Boolean aBoolean : check) {
            if (aBoolean) {
                count++;
            }
        }
        MongoUserHistory newOne = userListeningRepository.insert(new MongoUserHistory(userAnswer,
                userId, check, listeningScore.getScore(count)));
        log.info("User {} save user answer data", userId);
        return newOne.getId();
    }

    @Override
    public List<UserSimpleRecord> listAllListeningHistory(String userId) {
        List<MongoUserHistory> records = userListeningRepository.findAllByUserIdLike(userId);
        return records.stream().map(
                data -> {
                    Optional<MongoListeningExam> returnTest = listeningExamRepository.findById(data.getTestId());
                    return returnTest
                            .map(mongoListeningExam ->
                                    data.toUserSimpleRecord(mongoListeningExam.getExamName()))
                            .orElse(null);
                }
        ).toList();
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
    public ListeningSummary getListeningScore(String userId) {
        List<MongoUserHistory> records = userListeningRepository.findAllByUserIdLike(userId);
        List<IdName> exams = crudListeningService.listAllListeningExams();
        double averageScore = records.stream()
                .mapToDouble(MongoUserHistory::getScore)
                .average()
                .orElse(0.0); // Default to 0.0 if no scores

        // Select a random exam for next test ID and name1
        IdName nextTest = exams.isEmpty() ? null : exams.get(new Random().nextInt(exams.size()));

        return ListeningSummary.builder()
                .userId(userId)
                .averageScore(averageScore)
                .nextTestId(nextTest != null ? nextTest.getId() : null)
                .testName(nextTest != null ? nextTest.getTestName() : null)
                .build();
    }

}

