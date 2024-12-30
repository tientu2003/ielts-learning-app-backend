package com.project.listeningservice.internal.model.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserListeningRepository extends MongoRepository<MongoUserHistory,String> {
    List<MongoUserHistory> findAllByUserIdLike(String userId);
}
