package com.project.readingservice.internal;

import com.project.common.constraints.Topic;
import com.project.common.TopicProficiency;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.readingservice.internal.model.user.UserAnswerHistory;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReadingRepository extends MongoRepository<UserAnswerHistory, String> {

    List<UserAnswerHistory> findAllByUserIdLike(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $lookup: { from: 'reading_exam', as: 'test', let: { testId: '$testId' }, " +
                    "pipeline: [ { $match: { $expr: { $eq: ['$_id', { $toObjectId: '$$testId' }] } } }, { $project: { test_name: 1, _id: 0 } } ] } }",
            "{ $unwind: '$test' }",
            "{ $project: { id: '$_id', name: '$test.test_name', score: '$score', " +
                    "date: '$createdAt', topics: { $map: { input: '$topicProficiency', as: 'topic', in: '$$topic.topic' } } } }"
    })
    List<BasicUserRecordDTO> findAllByUserIdWithTestName(String userId);


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
}
