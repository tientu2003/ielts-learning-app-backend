package com.project.readingservice;

import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.external.data.ReadingAnswer;
import com.project.readingservice.external.data.ReadingExam;

import java.util.List;

public interface CRUDReadingService {

    ReadingExam getReadingTestData(String id);

    ReadingAnswer getAnswerTest(String testId);

    List<BasicExamDTO> listAllReadingTestName();

    List<String> listAllTopics();

}
