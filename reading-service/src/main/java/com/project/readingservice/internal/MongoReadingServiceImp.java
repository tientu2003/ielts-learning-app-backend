package com.project.readingservice.internal;

import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.external.data.*;
import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.external.errors.ReadingTestAlreadyExistsException;
import com.project.readingservice.internal.model.data.MongoReadingTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MongoReadingServiceImp implements CRUDReadingService {

    final ReadingTestRepository readingTestRepository;

    @Override
    public List<BasicExamDTO> listAllReadingTestName() {
        return readingTestRepository.findAllTestNames();
    }

    @Override
    public AnswerData getAnswerTest(String id) {
        Optional<MongoReadingTest> mongoReadingTest = readingTestRepository.findById(id);
        return mongoReadingTest.map(MongoReadingTest::toAnswerData).orElse(null);
    }

    @Override
    public ReadingTestData getReadingTestData(String id) {

        Optional<MongoReadingTest> mongoReadingTest = readingTestRepository.findById(id);

        return mongoReadingTest.map(MongoReadingTest::toReadingTestData).orElse(null);

    }

    @Override
    @Transactional
    public ReadingTestData createNewReadingTest(NewReadingTest newReadingTest) throws ReadingTestAlreadyExistsException {

        ReadingTestData readingTestData = getReadingTestData(newReadingTest.getName());

        if(readingTestData != null){
            throw new ReadingTestAlreadyExistsException( "A test with the name '" + newReadingTest.getName() + "' already exists.");
        }

        readingTestRepository.save(new MongoReadingTest(newReadingTest));

        List<MongoReadingTest> mongoReadingTests = readingTestRepository.findByTestName(newReadingTest.getName());

        if(mongoReadingTests.isEmpty()){
            return null;
        }

        return mongoReadingTests.getFirst().toReadingTestData();
    }


    @Override
    @Transactional
    public void deleteReadingTest(String id) {
        readingTestRepository.deleteById(id);
    }
}
