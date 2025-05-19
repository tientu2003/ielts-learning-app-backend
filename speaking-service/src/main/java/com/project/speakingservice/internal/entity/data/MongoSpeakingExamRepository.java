package com.project.speakingservice.internal.entity.data;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoSpeakingExamRepository extends MongoRepository<MongoSpeakingExam, String> {

    @Query("{ 'type': ?0 }")
    List<MongoSpeakingExam> findAllByType(Integer type);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$topic' } }",
            "{ $project: { _id: 0, topic: '$_id' } }",
            "{ $sort: { topic: 1 } }"
    })
    List<String> listAllTopics();
    
}
