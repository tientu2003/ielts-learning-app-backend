package com.project.readingservice.internal;

import com.project.readingservice.internal.model.user.UserAnswerHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReadingRepository extends MongoRepository<UserAnswerHistory, String> {

    List<UserAnswerHistory> findAllByUserIdLike(String userId);

}
