package com.project.readingservice.internal.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MongoQuestion {

    @Field(name = "question_number")
    private String questionNumber;

    @Field(name = "question_text")
    private String questionText;

    @Field(name = "answer")
    private String answer;

}
