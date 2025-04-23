package com.project.listeningservice.internal.model.user;

import com.project.common.dto.BasicUserRecordDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserListeningRepository extends MongoRepository<MongoUserHistory,String> {
    List<MongoUserHistory> findAllByUserIdLike(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $lookup: { from: 'listening_exam', as: 'test', let: { testId: '$testId' }, " +
                    "pipeline: [ { $match: { $expr: { $eq: ['$_id', { $toObjectId: '$$testId' }] } } }, " +
                    "{ $project: { test_name: 1, _id: 0 } } ] } }",
            "{ $unwind: '$test' }",
            "{ $project: { id: '$_id', name: '$test.test_name', score: '$score', date: '$createdAt', " +
                    "topics: { $map: { input: '$topicProficiency', as: 'topic', in: '$$topic.topic' } } } }"
    })
    List<BasicUserRecordDTO> findAllByUserIdWithTestName(String userId);

}
