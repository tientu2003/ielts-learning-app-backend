package com.project.writingservice.internal;

import com.project.writingservice.UserWritingService;
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

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWritingServiceImpl implements UserWritingService {

    private final WritingExamRepository writingExamRepository;

    private final UserWritingRecordRepository userWritingRecordRepository;

    private final AccountService accountService;

    private final ScoringService scoringService;

    @Override
    public void createUserAnswer(UserAnswer userAnswer) {
        String userId = "Temp";
        writingExamRepository.findById(userId).ifPresent(exam -> {
            WritingScore writingScore;
            if(exam.getTask() == 1){
                writingScore = scoringService.getWritingScoreTask1(exam.getContext(),exam.getDiagramUrl(),userAnswer.getAnswer());
            } else {
                writingScore = scoringService.getWritingScoreTask2(exam.getContext(),userAnswer.getAnswer());
            }
            userWritingRecordRepository.save(new MongoUserWritingRecord(userId, userAnswer, writingScore));
        });

    }

    @Override
    public DetailRecord getUserAnswer(String id) {

        return null;
    }

    @Override
    public List<UserSimpleRecord> getAllUserHistoryRecords() {

        return List.of();
    }

    @Override
    public WritingSummary getWritingSummary() {

        return null;
    }
}
