package com.project.speakingservice;

import com.project.common.dto.BasicExamDTO;
import com.project.speakingservice.external.data.SpeakingExam;

import java.util.List;

public interface CrudSpeakingService {

    List<BasicExamDTO> listAllSpeakingExam();

    SpeakingExam getSpeakingExamById(String id);

    List<String> listAllTopics();

}
