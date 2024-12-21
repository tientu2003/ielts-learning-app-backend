package com.project.readingservice;

import com.project.readingservice.external.data.*;

import java.util.List;

public interface CRUDReadingService {

    List<ExamData> listAllReadingTestName();
    
    AnswerData getAnswerTest(String testId);

    ReadingTestData getReadingTestData(String id);

    ReadingTestData createNewReadingTest(NewReadingTest newReadingTest) throws ReadingTestAlreadyExistsException;

    void deleteReadingTest(String id);
}
