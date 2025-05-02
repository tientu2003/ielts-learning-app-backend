package com.project.readingservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.readingservice.external.user.DetailRecord;
import com.project.readingservice.external.user.UserAnswer;

import java.util.List;

public interface UserReadingService {

    String saveUserAnswerData(UserAnswer userAnswer);

    List<BasicUserRecordDTO> listUserReadingTestHistory();

    DetailRecord getUserDetailReadingTestHistory(String recordId);

    UserSummary getReadingGeneralAssessment();

}
