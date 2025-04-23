package com.project.writingservice.internal;

import com.project.writingservice.CrudWritingService;
import com.project.writingservice.UserWritingService;
import com.project.writingservice.external.AiScoringServiceClient;
import com.project.writingservice.external.UserService;
import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.external.user.*;
import com.project.writingservice.internal.entity.MongoUserWritingRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserWritingServiceImpl implements UserWritingService {

    private final WritingExamRepository writingExamRepository;

    private final UserWritingRecordRepository userWritingRecordRepository;

    private final UserService userService;

    private final AiScoringServiceClient client;

    private final CrudWritingService crudWritingService;

    @Override
    public String createUserAnswer(UserAnswer userAnswer) {
        return writingExamRepository.findById(userAnswer.getExamId())
                .map(exam -> {
                    MongoUserWritingRecord newRecord = userWritingRecordRepository.save(new MongoUserWritingRecord(userAnswer, userService.getUserId()));
                    if (exam.getTask() == 2) {
                        sendUserAnswerToAi(newRecord.getId(),"Question", List.of("Answer"));
                    }

                    return newRecord.getId();
                })
                .orElse(null); // Return null if the exam is not found
    }

    @Async
    public void sendUserAnswerToAi(String recordId, String question, List<String> answer ) {
        AiScoringRequest request = new AiScoringRequest(recordId, question, answer);
        try{
            client.evaluateUserAnswer(request);
        }catch (Exception e){
            log.error("Ai Scoring Process Failed: {}", e.getMessage());
        }
    }


    @Override
    public DetailRecord getUserAnswer(String id) {

        Optional<MongoUserWritingRecord> optional = userWritingRecordRepository.findById(id);
        if(optional.isPresent()){
            MongoUserWritingRecord userWritingRecord = optional.get();
            WritingExam writingExam = crudWritingService.getWritingExamById(userWritingRecord.getExamId());
            return optional.get().toDetailRecord(writingExam);
        }
        return null;
    }

    @Override
    public List<UserSimpleRecord> getAllUserHistoryRecords() {
        return userWritingRecordRepository.findByUserIdLike(userService.getUserId()).parallelStream().map(e -> {
//            String name =  writingExamRepository.getNameById(e.getExamId());
            return e.toSimpleRecord("Test name"); // TODO missing name
        }).toList();
    }

    @Override
    public WritingSummary getWritingSummary() {
        String userId = userService.getUserId();
        List<MongoUserWritingRecord> list = userWritingRecordRepository.findByUserIdLike(userId);
        Double averageScore = list.stream().mapToDouble(e -> e.getScore().getFinalScore()).average().orElse(0.0);
        IdName nextExam = crudWritingService.getNextWritingExam();
        return WritingSummary.builder()
                .averageScore(averageScore)
                .totalTime("NOT FINISH!!") // TODO
                .nextTestId(nextExam.getId())
                .testName(nextExam.getName())
                .personalRecommendation("NO RECOMMENDATION!! NOT FINISH!!") // TODO
                .build();
    }
}
