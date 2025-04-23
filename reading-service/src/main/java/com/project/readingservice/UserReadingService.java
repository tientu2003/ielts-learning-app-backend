package com.project.readingservice;

import com.project.readingservice.external.user.BasicReadingHistory;
import com.project.readingservice.external.user.DetailReadingTestRecord;
import com.project.readingservice.external.user.GeneralAssessment;
import com.project.readingservice.external.user.UserAnswer;

import java.util.List;
import java.util.UUID;

public interface UserReadingService {

    DetailReadingTestRecord saveUserAnswerData(UserAnswer userAnswer);

    List<BasicReadingHistory> listUserReadingTestHistory();

    DetailReadingTestRecord getUserDetailReadingTestHistory(String recordId);

    GeneralAssessment getReadingGeneralAssessment();

}
