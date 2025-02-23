package com.project.writingservice.internal;

import com.project.writingservice.internal.entity.MongoWritingExam;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WritingExamRepository extends MongoRepository<MongoWritingExam, String> {
}
