package com.project.readingservice.internal.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MongoQuestionContext {

    @Field(name = "data")
    private String data;

}
