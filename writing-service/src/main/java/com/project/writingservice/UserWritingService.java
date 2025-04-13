package com.project.writingservice;

import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;

import java.util.List;

public interface UserWritingService {

    String createUserAnswer(UserAnswer userAnswer);

    DetailRecord getUserAnswer(String id);

    List<UserSimpleRecord> getAllUserHistoryRecords();

    WritingSummary getWritingSummary();
}
