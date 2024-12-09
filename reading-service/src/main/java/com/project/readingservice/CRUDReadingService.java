package com.project.readingservice;

import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.data.NewReadingTest;
import com.project.readingservice.external.data.ReadingTestAlreadyExistsException;
import com.project.readingservice.external.data.ReadingTestData;

import java.util.List;

public interface CRUDReadingService {

    List<String> listAllReadingTestName();
    
    AnswerData getAnswerTest(String testId);

    ReadingTestData getReadingTestData(String testName);

    ReadingTestData createNewReadingTest(NewReadingTest newReadingTest) throws ReadingTestAlreadyExistsException;

    void deleteReadingTest(String id);
}
