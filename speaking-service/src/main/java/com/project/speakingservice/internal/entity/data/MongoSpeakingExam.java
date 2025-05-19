package com.project.speakingservice.internal.entity.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Data
@NoArgsConstructor
@Document(collation = "speaking_exam")
public class MongoSpeakingExam {

    @Id
    private String id;

    @Field
    private Integer type;

    @Field
    private String topic;

    @Field
    private List<MongoIdQuestion> questionsOne;

    @Field
    private MongoIdQuestion questionsTwo;

    @Field
    private List<MongoIdQuestion> questionsThree;

    @Data
    @NoArgsConstructor
    public static class MongoIdQuestion{
        @Field
        private Integer number;
        @Field
        private String question;
    }

}
