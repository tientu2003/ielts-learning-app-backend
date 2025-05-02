package com.project.readingservice.internal;

import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.external.data.*;
import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.internal.model.data.BasicInfoExam;
import com.project.readingservice.internal.model.data.MongoReadingExam;
import com.project.readingservice.internal.model.data.ReadingTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MongoReadingServiceImp implements CRUDReadingService {

    final ReadingTestRepository readingTestRepository;

    @Override
    public List<BasicExamDTO> listAllReadingTestName() {
        return readingTestRepository.findAllTestNames().parallelStream().map(BasicInfoExam::toDTO).toList();
    }

    @Override
    public ReadingAnswer getAnswerTest(String id) {
        Optional<MongoReadingExam> mongoReadingTest = readingTestRepository.findById(id);
        return mongoReadingTest.map(MongoReadingExam::toAnswerData).orElse(null);
    }

    @Override
    public ReadingExam getReadingTestData(String id) {
        Optional<MongoReadingExam> mongoReadingTest = readingTestRepository.findById(id);
        return mongoReadingTest.map(MongoReadingExam::toReadingExam).orElse(null);
    }

    @Override
    public List<String> listAllTopics() {
        return readingTestRepository.findAllDistinctTopic();
    }

}
