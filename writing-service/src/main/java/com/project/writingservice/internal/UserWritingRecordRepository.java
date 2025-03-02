package com.project.writingservice.internal;

import com.project.writingservice.internal.entity.MongoUserWritingRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserWritingRecordRepository extends MongoRepository<MongoUserWritingRecord, String> {
    List<MongoUserWritingRecord> findByUserIdLike(String userId);
}
