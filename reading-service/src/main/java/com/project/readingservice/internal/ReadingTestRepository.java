package com.project.readingservice.internal;

import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.internal.model.data.MongoReadingTest;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReadingTestRepository extends MongoRepository<MongoReadingTest, String> {

    @Aggregation(pipeline = {
            "{ $project: { id: '$_id', name: '$test_name' } }"
    })
    List<BasicExamDTO> findAllTestNames();

    List<MongoReadingTest> findByTestName(String testName);

}
