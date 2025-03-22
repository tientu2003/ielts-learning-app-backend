package com.project.speakingservice;

import com.project.speakingservice.external.SpeakingExam;
import com.project.speakingservice.external.SpeakingList;

import java.util.List;

public interface CrudSpeakingService {

    SpeakingExam getSpeakingExam(String id);

    List<SpeakingList> getAllSpeakingExamList();

    SpeakingExam getNextSpeakingExam(String userId);
}
