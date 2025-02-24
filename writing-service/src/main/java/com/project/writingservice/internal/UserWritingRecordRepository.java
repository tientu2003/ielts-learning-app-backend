package com.project.writingservice.internal;

import com.project.writingservice.internal.entity.MongoUserWritingRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserWritingRecordRepository extends MongoRepository<MongoUserWritingRecord, String> {
}
