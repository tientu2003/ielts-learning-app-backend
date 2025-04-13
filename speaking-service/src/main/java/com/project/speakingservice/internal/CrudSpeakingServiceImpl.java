package com.project.speakingservice.internal;

import com.project.speakingservice.CrudSpeakingService;
import com.project.speakingservice.external.SpeakingExam;
import com.project.speakingservice.external.SpeakingList;

import java.util.List;

public class CrudSpeakingServiceImpl implements CrudSpeakingService {
    @Override
    public SpeakingExam getSpeakingExam(String id) {
        return null;
    }

    @Override
    public List<SpeakingList> getAllSpeakingExamList() {
        return List.of();
    }

    @Override
    public SpeakingExam getNextSpeakingExam(String userId) {
        return null;
    }
}
