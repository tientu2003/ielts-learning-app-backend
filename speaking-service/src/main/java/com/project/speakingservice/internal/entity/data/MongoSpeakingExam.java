package com.project.speakingservice.internal.entity.data;

import com.project.common.dto.BasicExamDTO;
import com.project.speakingservice.external.IdQuestion;
import com.project.speakingservice.external.data.SpeakingExam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Data
@NoArgsConstructor
@Document(collection = "speaking_exam")
public class MongoSpeakingExam {

    @Id
    private String id;

    @Field
    private String topic;

    @Field
    private Integer type;

    @Field("questionsOne")
    private List<MongoIdQuestion> questionsOne;

    @Field("questionsTwo")
    private String questionsTwo;

    @Field("questionsThree")
    private List<MongoIdQuestion> questionsThree;

    @Data
    public static class MongoIdQuestion{
        @Field("number")
        private Integer number;
        @Field("question")
        private String question;
    }

    public BasicExamDTO basicExamDTO(){
        return BasicExamDTO.builder()
                .id(this.id)
                .testName("Speaking Test: " + this.getTopic())
                .topics(List.of(this.topic))
                .build();
    }
    public SpeakingExam toSpeakingExam(){
        return SpeakingExam.builder()
                .id(this.id)
                .type(this.type)
                .partOne(this.questionsOne.stream()
                        .map(q -> IdQuestion.builder()
                                .number(q.getNumber())
                                .topic(this.topic)
                                .question(q.getQuestion())
                                .build())
                        .toList())
                .partTwo(IdQuestion.builder()
                        .number(1)
                        .topic(this.questionsTwo)
                        .question(this.questionsTwo)
                        .build())
                .partThree(this.questionsThree.stream()
                        .map(q -> IdQuestion.builder()
                                .number(q.getNumber())
                                .topic(this.topic)
                                .question(q.getQuestion())
                                .build())
                        .toList())
                .build();
    }


}
