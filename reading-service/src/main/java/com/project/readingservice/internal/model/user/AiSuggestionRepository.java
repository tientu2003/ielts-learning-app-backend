package com.project.readingservice.internal.model.user;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AiSuggestionRepository extends MongoRepository<AiSuggestion, String> {
    @Query("{ 'userId': ?0 }")
    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $sort: { createdAt: -1 } }",
            "{ $limit: 1 }"
    })
    AiSuggestion findByUserIdWithLastestDate(String userId);
}
