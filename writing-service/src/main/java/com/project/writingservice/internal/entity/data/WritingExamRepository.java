package com.project.writingservice.internal.entity.data;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WritingExamRepository extends MongoRepository<MongoWritingExam, String> {
    List<MongoWritingExam> findAll();

    @Aggregation(pipeline = {
            "{ $group: { _id:   '$topic' } }",
            "{ $project: { _id: 0, topics: '$_id' } }"
    })
    List<String> listAllTopics();
}
