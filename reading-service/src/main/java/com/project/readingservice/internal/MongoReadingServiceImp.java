package com.project.readingservice.internal;

import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.data.NewReadingTest;
import com.project.readingservice.external.data.ReadingTestAlreadyExistsException;
import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.external.data.ReadingTestData;
import com.project.readingservice.internal.model.data.MongoReadingTest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MongoReadingServiceImp implements CRUDReadingService {

    private ReadingTestRepository readingTestRepository;

    @Autowired
    public MongoReadingServiceImp(ReadingTestRepository readingTestRepository) {
        this.readingTestRepository = readingTestRepository;
    }

    @Override
    public List<String> listAllReadingTestName() {
        return readingTestRepository.findAllTestNames();
    }

    @Override
    public AnswerData getAnswerTest(String id) {
        Optional<MongoReadingTest> mongoReadingTest = readingTestRepository.findById(id);
        return mongoReadingTest.map(MongoReadingTest::toAnswerData).orElse(null);
    }

    @Override
    public ReadingTestData getReadingTestData(String testName) {

        List<MongoReadingTest> mongoReadingTest = readingTestRepository.findByTestName(testName);
        if (mongoReadingTest.isEmpty()) {
            return null;
        }
        return mongoReadingTest.getFirst().toReadingTestData();
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
