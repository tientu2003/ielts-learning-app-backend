package com.project.writingservice.internal;

import com.project.writingservice.internal.entity.MongoWritingExam;
import com.project.writingservice.internal.projection.IdNameProjection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WritingExamRepository extends MongoRepository<MongoWritingExam, String> {
    List<IdNameProjection> getAll();
}
