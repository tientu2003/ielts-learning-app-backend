package com.project.speakingservice.internal.entity.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoUserHistoryRepository extends MongoRepository<MongoUserHistory, String> {
    List<MongoUserHistory> findAllByUserId(String userId);
}
