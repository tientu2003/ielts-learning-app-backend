package com.project.readingservice.internal;

import com.project.readingservice.internal.model.MongoReadingTest;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface   ReadingTestRepository extends MongoRepository<MongoReadingTest, String> {

    @Aggregation(pipeline = {
            "{ $project: { test_name: 1, _id: 0 } }"
    })
    List<String> findAllTestNames();

    List<MongoReadingTest> findByTestName(String testName);

    @Aggregation(pipeline = {
            "{ $unwind: '$passages' }",
            "{ $project: { articleTitle: '$passages.article_title', _id: 0 } }"
    })
    List<String> findPassagesArticleTitle();


}
