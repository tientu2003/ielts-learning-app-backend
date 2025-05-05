package com.project.writingservice.internal.entity.data;

import com.project.writingservice.external.data.BasicWritingDTO;
import com.project.writingservice.external.data.WritingExam;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

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

    @Field(name = "test_name")
    private String testName;

    @Field(name = "topic")
    private String topic;

    @Field(name = "difficulty")
    private Double difficulty;

    public WritingExam toWritingExam() {
        return WritingExam.builder()
                .id(this.id)
                .context(this.context)
                .task(this.task)
                .diagram_url(this.diagramUrl)
                .name(this.testName)
                .build();
    }

    public BasicWritingDTO toBasicExamDTO() {
        BasicWritingDTO one = new BasicWritingDTO();
        one.setId(this.id);
        one.setTask(this.task);
        one.setTestName(this.testName);
        one.setTopics(List.of(this.topic));
        return one;
    }
}
