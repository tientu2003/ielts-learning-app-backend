package com.project.readingservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.readingservice.external.user.DetailReadingTestRecord;
import com.project.readingservice.external.user.GeneralAssessment;
import com.project.readingservice.external.user.UserAnswer;

import java.util.List;

public interface UserReadingService {

    DetailReadingTestRecord saveUserAnswerData(UserAnswer userAnswer);

    List<BasicUserRecordDTO> listUserReadingTestHistory();

    DetailReadingTestRecord getUserDetailReadingTestHistory(String recordId);

    GeneralAssessment getReadingGeneralAssessment();

}
