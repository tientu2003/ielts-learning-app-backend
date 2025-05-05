package com.project.writingservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;

import java.util.List;

public interface UserWritingService {

    String createUserAnswer(UserAnswer userAnswer);

    DetailRecord getUserAnswer(String id);

    List<BasicUserRecordDTO> getAllUserHistoryRecords();

    UserSummary getWritingSummary();
}
