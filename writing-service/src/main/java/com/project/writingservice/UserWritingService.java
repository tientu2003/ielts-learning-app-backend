package com.project.writingservice;

import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;

import java.util.List;

public interface UserWritingService {

    void createUserAnswer(UserAnswer userAnswer);

    DetailRecord getUserAnswer(String id);

    List<UserSimpleRecord> getAllUserHistoryRecords(String userId);

    WritingSummary getWritingSummary(String userId);
}
