package com.project.writingservice.internal.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "writing_exam")
public class MongoWritingExam {
    @MongoId
    private String id;

    @Field(name = "context")
    private String context;

    @Field(name = "diagram_url")
    private String diagramUrl;

    @Field(name = "task")
    private Integer task;
}
