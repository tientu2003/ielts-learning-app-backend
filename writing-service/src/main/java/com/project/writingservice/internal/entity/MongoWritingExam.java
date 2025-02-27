package com.project.writingservice.internal.entity;

import com.project.writingservice.external.data.WritingExam;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "writing_exam")
public class MongoWritingExam {
    @Id
    private String id;

    @Field(name = "context")
    private String context;

    @Field(name = "diagram_url")
    private String diagramUrl;

    @Field(name = "task")
    private Integer task;

    public WritingExam toWritingExam() {
        return WritingExam.builder()
                .id(this.id)
                .context(this.context)
                .task(this.task)
                .diagram_url(this.diagramUrl)
                .name(this.task == 1 ? "Task 1: " + this.context : "Task 2: " + this.context)
                .build();
    }
}
