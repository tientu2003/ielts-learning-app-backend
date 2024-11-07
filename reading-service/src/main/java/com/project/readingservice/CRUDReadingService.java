package com.project.readingservice;

import com.project.readingservice.external.AnswerData;
import com.project.readingservice.external.NewReadingTest;
import com.project.readingservice.external.ReadingTestData;

import java.util.List;

public interface CRUDReadingService {

    List<String> listAllReadingTestName();
    
    AnswerData getAnswerTest(String testId);

    ReadingTestData getReadingTestData(String testName);

    ReadingTestData createNewReadingTest(NewReadingTest newReadingTest);

    void deleteReadingTest(String id);
}
