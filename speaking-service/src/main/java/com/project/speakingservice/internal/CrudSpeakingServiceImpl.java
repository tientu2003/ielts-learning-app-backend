package com.project.speakingservice.internal;

import com.project.common.dto.BasicExamDTO;
import com.project.speakingservice.CrudSpeakingService;
import com.project.speakingservice.external.data.SpeakingExam;
import com.project.speakingservice.internal.entity.data.MongoSpeakingExam;
import com.project.speakingservice.internal.entity.data.MongoSpeakingExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudSpeakingServiceImpl implements CrudSpeakingService {

    final MongoSpeakingExamRepository mongoSpeakingExamRepository;

    @Override
    public List<BasicExamDTO> listAllSpeakingExam() {
        List<MongoSpeakingExam> data = mongoSpeakingExamRepository.findAll();
        return data.parallelStream().map(MongoSpeakingExam::basicExamDTO).toList();
    }

    @Override
    public SpeakingExam getSpeakingExamById(String id) {
        MongoSpeakingExam exam = mongoSpeakingExamRepository.findById(id).orElseThrow(null);
        if(exam == null) {
            return null;
        }
        return exam.toSpeakingExam();
    }

    @Override
    public List<String> listAllTopics() {
        return mongoSpeakingExamRepository.listAllTopics();
    }
}
