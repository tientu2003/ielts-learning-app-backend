package com.project.speakingservice.internal.entity.data;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoSpeakingExamRepository extends MongoRepository<MongoSpeakingExam, String> {

    List<MongoSpeakingExam> findAllByType(Integer type);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$topic' } }",
            "{ $project: { _id: 0, topic: '$_id' } }",
            "{ $sort: { topic: 1 } }"
    })
    List<String> listAllTopics();
    
}
