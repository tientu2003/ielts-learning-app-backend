package com.project.readingservice.internal.model.user;

import com.project.common.TopicProficiency;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.MetricAverage;
import com.project.common.dto.TopicAverage;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserReadingRepository extends MongoRepository<UserAnswerHistory, String> {
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
    List<TopicProficiency> findAllTopicProficiencyByUserIdAndTopic(String userId, String topic);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: null, averageScore: { $avg: '$score' } } }",
            "{ $project: { _id: 0, averageScore: 1 } }"
    })
    Double calculateAverageScoreByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0, createdAt: { $gte: ?1 } } }",
            "{ $project: { testId: 1 } }"
    })
    List<String> findByUserIdAndDateAfter(String userId, Date twoMonthsAgo);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $group: { _id: null, " +
                    "averageDifficulty: { $avg: '$topicProficiency.difficulty' }, " +
                    "averageScore: { $avg: '$score' }, " +
                    "averageAccuracy: { $avg: '$topicProficiency.accuracy' } } }",
            "{ $project: { _id: 0,  averageDifficulty: 1, averageScore: 1, averageAccuracy: 1  } }"
    })
    MetricAverage calculateAverageMetricsByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $group: { _id: '$topicProficiency.topic' } }",
            "{ $project: { _id: 0, topic: '$_id' } }"
    })
    List<String> findDistinctTopicsByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $unwind: '$topicProficiency' }",
            "{ $group: { _id: '$topicProficiency.topic', averageScore: { $avg: '$score' } } }",
            "{ $project: { _id: 0, topic: '$_id', averageScore: 1 } }"
    })
    List<TopicAverage> calculateAverageScoreByUserIdGroupByTopic(String userId);
}
