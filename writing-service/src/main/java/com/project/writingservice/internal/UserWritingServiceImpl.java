package com.project.writingservice.internal;

import com.project.writingservice.UserWritingService;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWritingServiceImpl implements UserWritingService {

    private final WritingExamRepository writingExamRepository;

    private final UserWritingRecordRepository userWritingRecordRepository;

    @Override
    public void createUserAnswer(UserAnswer userAnswer) {

    }

    @Override
    public DetailRecord getUserAnswer(String id) {
        return null;
    }

    @Override
    public List<UserSimpleRecord> getAllUserHistoryRecords(String userId) {
        return List.of();
    }

    @Override
    public WritingSummary getWritingSummary(String userId) {
        return null;
    }
}
