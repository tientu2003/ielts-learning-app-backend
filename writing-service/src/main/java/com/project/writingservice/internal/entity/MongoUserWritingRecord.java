package com.project.writingservice.internal.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Document("writing_user")
public class MongoUserWritingRecord {
    @MongoId
    private String id;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "exam_id")
    private String examId;

    @Field(name = "create_at")
    private Date createAt;

    @Field(name = "duration")
    private String duration;

    @Field(name = "score")
    private Double score;
}
