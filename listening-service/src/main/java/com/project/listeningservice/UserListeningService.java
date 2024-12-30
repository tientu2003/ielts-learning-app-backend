package com.project.listeningservice;

import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.ListeningSummary;
import com.project.listeningservice.external.user.UserAnswer;
import com.project.listeningservice.external.user.UserSimpleRecord;


import java.util.List;


public interface UserListeningService {

    String createUserAnswer(UserAnswer userAnswer, String userId);

    List<UserSimpleRecord> listAllListeningHistory(String userId);

    DetailRecord getListeningDetailRecord(String recordId);

    ListeningSummary getListeningScore(String userId);
}
