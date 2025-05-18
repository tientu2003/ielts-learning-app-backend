package com.project.speakingservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.speakingservice.external.user.DetailRecord;
import com.project.speakingservice.external.user.UserAnswer;

import java.util.List;

public interface UserSpeakingService {
    String saveUserSpeaking(UserAnswer userAnswer);

    List<BasicUserRecordDTO> listUserSpeakingHistory();

    DetailRecord getUserSpeakingDetailRecord(String recordId);
}
