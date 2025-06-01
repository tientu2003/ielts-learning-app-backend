package com.project.writingservice.internal.entity.user;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.TopicAverage;
import com.project.writingservice.internal.util.WritingScore;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface UserWritingRecordRepository extends MongoRepository<MongoUserWritingRecord, String> {
    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $lookup: { from: 'writing_exam', as: 'test', let: { testId: '$examId' }, " +
                    "pipeline: [ { $match: { $expr: { $eq: ['$_id', { $toObjectId: '$$testId' }] } } }, " +
                    "{ $project: { test_name: 1, _id: 0 } } ] } }",
            "{ $unwind: '$test' }",
            "{ $project: { id: '$_id', name: '$test.test_name', score: { $ifNull: ['$score.finalScore', null] }, date: '$createAt', " +
                    "topics: ['$topic'] } }"
    })
    List<BasicUserRecordDTO> findAllByUserIdWithTestName(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: '$userId', averageScore: { $avg: '$score.finalScore' } } }"
    })
    Double getAverageFinalScoreByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0, topic: ?1 } }",
            "{ $match: { 'score': { $exists: true } } }",
            "{ $project: { 'score': 1 } }",
            "{ $replaceRoot: { newRoot: '$score' } }"
    })
    List<WritingScore> findAllWritingScoreByUserIdAndTopic(String userId, String topic);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: '$topic'} }",
            "{ $project: { _id: 0, topics: '$_id' } }"
    })
    List<String> findDistinctTopicsByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: '$topic', averageScore: { $avg: '$score.finalScore' } } }",
            "{ $project: { _id: 0, topic: '$_id', averageScore: 1 } }"
    })
    List<TopicAverage> calculateAverageScoreByUserIdGroupByTopic(String userId);


    @Aggregation(pipeline = {
            "{ $match: { userId: ?0, createAt: { $gt: ?1 } } }",
            "{ $project: { examId: 1, _id: 0 } }"
    })
    List<String> findByUserIdAndDateAfter(String userId, Date twoMonthsAgo);
}
