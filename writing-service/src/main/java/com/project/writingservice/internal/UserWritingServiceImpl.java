package com.project.writingservice.internal;

import com.project.writingservice.UserWritingService;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWritingServiceImpl implements UserWritingService {
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
