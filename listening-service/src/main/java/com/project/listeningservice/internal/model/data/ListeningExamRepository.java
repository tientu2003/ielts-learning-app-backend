package com.project.listeningservice.internal.model.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ListeningExamRepository extends MongoRepository<MongoListeningExam, String> {
    @Query(value = "{}", fields = "{'_id': 1,'test_name': 1, 'topic_1': 1, 'topic_2': 1,'topic_3': 1,'topic_4': 1}")
    List<MongoIdName> getAllIdNames();
}
