package com.project.listeningservice.internal.model.data;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ListeningExamRepository extends MongoRepository<MongoListeningExam, String> {
    @Query(value = "{}", fields = "{'_id': 1,'test_name': 1, 'topics': 1, 'difficulties': 1 ,'levels': 1}")
    List<MongoIdName> getAllIdNames();

    @Aggregation(pipeline = {
            "{ $unwind: '$topics' }",
            "{ $group: { _id: '$topics' } }",
            "{ $project: { _id: 0, topic: '$_id' } }"
    })
    List<String> findDistinctTopics();

}
