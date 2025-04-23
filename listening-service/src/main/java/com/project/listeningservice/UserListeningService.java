package com.project.listeningservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.UserAnswer;

import java.util.List;


public interface UserListeningService {

    String createUserAnswer(UserAnswer userAnswer);

    List<BasicUserRecordDTO> listAllListeningHistory();

    DetailRecord getListeningDetailRecord(String recordId);

    UserSummary getListeningScore();
}
