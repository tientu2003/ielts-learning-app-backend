package com.project.speakingservice.internal;

import com.project.common.dto.BasicExamDTO;
import com.project.speakingservice.CrudSpeakingService;
import com.project.speakingservice.external.IdQuestion;
import com.project.speakingservice.external.data.SpeakingExam;
import com.project.speakingservice.internal.entity.data.MongoSpeakingExam;
import com.project.speakingservice.internal.entity.data.MongoSpeakingExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudSpeakingServiceImpl implements CrudSpeakingService {

    final MongoSpeakingExamRepository mongoSpeakingExamRepository;

    @Override
    public List<BasicExamDTO> listAllSpeakingExam() {

        // type 1 only have questionsOne, topic 
        List<MongoSpeakingExam> partOne = mongoSpeakingExamRepository.findAllByType(1);
        // type 2 only have questionsTwo, questionsThree, topic
        List<MongoSpeakingExam> partTwo = mongoSpeakingExamRepository.findAllByType(2);

        List<BasicExamDTO> result = new ArrayList<>();

        partOne.parallelStream().forEach(one -> {
            partTwo.parallelStream().forEach(two -> {
                result.add(BasicExamDTO.builder()
                        .id(one.getId() + "_" + two.getId())
                        .testName("Speaking Test: " + one.getTopic() + " - " + two.getTopic())
                        .topics(List.of(one.getTopic(), two.getTopic()))
                        .build());
            });
        });
        return result;
    }
    
    

    @Override
    public SpeakingExam getSpeakingExamById(String id) {
        String[] ids = id.split("_");
        MongoSpeakingExam partOne = mongoSpeakingExamRepository.findById(ids[0]).orElseThrow();
        MongoSpeakingExam partTwo = mongoSpeakingExamRepository.findById(ids[1]).orElseThrow();

        return SpeakingExam.builder()
                .id(id)
                .partOne(partOne.getQuestionsOne().stream()
                        .map(q -> IdQuestion.builder()
                                .number(q.getNumber())
                                .topic(partOne.getTopic())
                                .question(q.getQuestion())
                                .build())
                        .toList())
                .partTwo(IdQuestion.builder()
                        .number(1)
                        .topic(partTwo.getTopic())
                        .question(partTwo.getQuestionsTwo())
                        .build())
                .partThree(partTwo.getQuestionsThree().stream()
                        .map(q -> IdQuestion.builder()
                                .number(q.getNumber())
                                .topic(partTwo.getTopic())
                                .question(q.getQuestion())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public List<String> listAllTopics() {
        return mongoSpeakingExamRepository.listAllTopics();
    }
}
