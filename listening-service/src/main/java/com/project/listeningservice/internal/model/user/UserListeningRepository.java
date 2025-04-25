package com.project.listeningservice.internal.model.user;

import com.project.common.constraints.Topic;
import com.project.common.TopicProficiency;
import com.project.common.dto.BasicUserRecordDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;

public interface UserListeningRepository extends MongoRepository<MongoUserHistory,String> {

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


    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $group: { _id: '$topicProficiency.topic' } }",
            "{ $replaceRoot: { newRoot: '$_id' } }"
    })
    List<Topic> findDistinctTopicsByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0, 'topicProficiency.topic': ?1 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $match: { 'topicProficiency.topic': ?1 } }",
            "{ $replaceRoot: { newRoot: '$topicProficiency' } }"
    })
    List<TopicProficiency> findAllTopicProficiencyByUserIdAndTopic(String userId, Topic topic);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: null, averageScore: { $avg: '$score' } } }",
            "{ $project: { _id: 0, averageScore: 1 } }"
    })
    Double calculateAverageScoreByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $group: { _id: '$topicProficiency.topic', averageScore: { $avg: '$score' } } }",
            "{ $project: { _id: 0, topic: '$_id', averageScore: 1 } }"
    })
    Map<Topic, Double> calculateAverageScoreByUserIdGroupByTopic(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $group: { _id: null, " +
                    "averageDifficulty: { $avg: '$topicProficiency.difficulty' }, " +
                    "averageScore: { $avg: '$score' }, " +
                    "averageAccuracy: { $avg: '$topicProficiency.accuracy' } } }",
            "{ $project: { _id: 0, averageDifficulty: 1, averageScore: 1, averageAccuracy: 1 } }"
    })
    Map<String, Double> calculateAverageMetricsByUserId(String userId);
}
