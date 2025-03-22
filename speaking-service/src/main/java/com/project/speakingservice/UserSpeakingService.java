package com.project.speakingservice;

import com.project.speakingservice.external.UserAnswer;
import com.project.speakingservice.external.UserHistory;
import com.project.speakingservice.external.UserRecord;

import java.util.List;

public interface UserSpeakingService {

    void saveNewUserAnswer(UserAnswer userAnswer);

    UserRecord getUserRecord(String userId, String id);

    List<UserHistory> getUserHistory(String userId);
}
