package com.project.readingservice;

import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.external.data.*;
import com.project.readingservice.external.errors.ReadingTestAlreadyExistsException;

import java.util.List;

public interface CRUDReadingService {

    List<BasicExamDTO> listAllReadingTestName();
    
    AnswerData getAnswerTest(String testId);

    ReadingTestData getReadingTestData(String id);

    ReadingTestData createNewReadingTest(NewReadingTest newReadingTest) throws ReadingTestAlreadyExistsException;

    void deleteReadingTest(String id);
}
