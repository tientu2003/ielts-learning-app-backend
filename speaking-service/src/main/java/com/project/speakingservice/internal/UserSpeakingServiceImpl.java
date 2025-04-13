package com.project.speakingservice.internal;

import com.project.speakingservice.UserSpeakingService;
import com.project.speakingservice.external.UserAnswer;
import com.project.speakingservice.external.UserHistory;
import com.project.speakingservice.external.UserRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSpeakingServiceImpl implements UserSpeakingService {
    @Override
    public void saveNewUserAnswer(UserAnswer userAnswer) {
    }

    @Override
    public UserRecord getUserRecord(String userId, String id) {
        return null;
    }

    @Override
    public List<UserHistory> getUserHistory(String userId) {
        return List.of();
    }
}
