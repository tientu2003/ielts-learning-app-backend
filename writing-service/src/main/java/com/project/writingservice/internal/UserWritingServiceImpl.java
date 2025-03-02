package com.project.writingservice.internal;

import com.project.writingservice.CrudWritingService;
import com.project.writingservice.UserWritingService;
import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;
import com.project.writingservice.internal.entity.MongoUserWritingRecord;
import com.project.writingservice.internal.util.WritingScore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWritingServiceImpl implements UserWritingService {

    private final WritingExamRepository writingExamRepository;

    private final UserWritingRecordRepository userWritingRecordRepository;


    private final ScoringService scoringService;

    private final CrudWritingService crudWritingService;

    @Override
    public String createUserAnswer(UserAnswer userAnswer) {
        return writingExamRepository.findById(userAnswer.getExamId())
            .map(exam -> {
                WritingScore writingScore;
                if (exam.getTask() == 1) {
                    writingScore = scoringService.getWritingScoreTask1(exam.getContext(), exam.getDiagramUrl(), userAnswer.getAnswer());
                } else {
                    writingScore = scoringService.getWritingScoreTask2(exam.getContext(), userAnswer.getAnswer());
                }
                MongoUserWritingRecord newRecord = userWritingRecordRepository.save(new MongoUserWritingRecord(userAnswer, writingScore));
                return newRecord.getId();
            })
            .orElse(null); // Return null if the exam is not found
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
    public List<UserSimpleRecord> getAllUserHistoryRecords(String userId) {
        return userWritingRecordRepository.findByUserIdLike(userId).parallelStream().map(e -> {
//            String name =  writingExamRepository.getNameById(e.getExamId());
            return e.toSimpleRecord("Test name"); // TODO missing name
        }).toList();
    }

    @Override
    public WritingSummary getWritingSummary(String userId) {
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
