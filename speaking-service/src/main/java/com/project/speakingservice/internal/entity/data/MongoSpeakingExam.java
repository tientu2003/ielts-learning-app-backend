package com.project.speakingservice.internal.entity.data;

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

}
