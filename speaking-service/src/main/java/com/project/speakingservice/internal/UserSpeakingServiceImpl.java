package com.project.speakingservice.internal;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.speakingservice.UserSpeakingService;
import com.project.speakingservice.external.UserService;
import com.project.speakingservice.external.user.DetailRecord;
import com.project.speakingservice.external.user.UserAnswer;
import com.project.speakingservice.internal.entity.user.MongoUserHistory;
import com.project.speakingservice.internal.entity.user.MongoUserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserSpeakingServiceImpl implements UserSpeakingService {

    final MongoUserHistoryRepository mongoUserHistoryRepository;

    final UserService userService;

    @Override
    public String saveUserSpeaking(UserAnswer userAnswer) {
        String userId = userService.getUserId();
        MongoUserHistory data = mongoUserHistoryRepository.save(new MongoUserHistory(userId, userAnswer));
        return data.getId();
    }

    @Override
    public List<BasicUserRecordDTO> listUserSpeakingHistory() {
        String userId = userService.getUserId();
        return mongoUserHistoryRepository.findAllByUserId(userId).parallelStream().map(MongoUserHistory::toBasicUserRecordDTO).toList();
    }

    @Override
    public DetailRecord getUserSpeakingDetailRecord(String recordId) {
        return mongoUserHistoryRepository.findById(recordId).map(MongoUserHistory::toDetailRecord).orElse(null);
    }
}
